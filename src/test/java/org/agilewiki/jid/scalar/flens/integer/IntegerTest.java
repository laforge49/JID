package org.agilewiki.jid.scalar.flens.integer;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class IntegerTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());

            IntegerJid int1 = (IntegerJid) IntegerJidFactory.fac.newActor(factory.getMailbox(), factory);
            IntegerJid int2 = (IntegerJid) (new CopyJID()).send(future, int1);
            (new SetInteger(1)).send(future, int2);
            IntegerJid int3 = (IntegerJid) (new CopyJID()).send(future, int2);

            int sl = GetSerializedLength.req.send(future, int1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, int2);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, int3);
            assertEquals(4, sl);

            int v = GetInteger.req.send(future, int1);
            assertEquals(0, v);
            v = GetInteger.req.send(future, int2);
            assertEquals(1, v);
            v = GetInteger.req.send(future, int3);
            assertEquals(1, v);

            Actor jidJid1 = ActorJidFactory.fac.newActor(factory.getMailbox(), factory);
            SetActor sjvi = new SetActor(JidFactories.INTEGER_JID_TYPE);
            sjvi.send(future, jidJid1);
            IntegerJid rpa = (IntegerJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetInteger.req.send(future, rpa);
            assertEquals(0, v);
            (new SetInteger(-1)).send(future, rpa);
            rpa = (IntegerJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetInteger.req.send(future, rpa);
            assertEquals(-1, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
