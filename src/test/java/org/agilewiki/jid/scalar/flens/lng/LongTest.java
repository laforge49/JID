package org.agilewiki.jid.scalar.flens.lng;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.jidsFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class LongTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            Actor factory = new JidFactories(mailboxFactory.createMailbox());
            factory.setParent(null);

            NewJID newLongJid = new NewJID(JidFactories.LONG_JID_TYPE, factory.getMailbox(), factory);
            LongJid long1 = (LongJid) newLongJid.send(future, factory).thisActor();
            LongJid long2 = (LongJid) (new CopyJID()).send(future, long1);
            (new SetLong(1L)).send(future, long2);
            LongJid float3 = (LongJid) (new CopyJID()).send(future, long2);

            int sl = GetSerializedLength.req.send(future, long1);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, long2);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, float3);
            assertEquals(8, sl);

            long v = GetLong.req.send(future, long1);
            assertEquals(0L, v);
            v = GetLong.req.send(future, long2);
            assertEquals(1L, v);
            v = GetLong.req.send(future, float3);
            assertEquals(1L, v);

            Actor jidJid1 = (new ActorJidFactory()).newActor(factory.getMailbox(), factory);
            SetActor sjvl = new SetActor(JidFactories.LONG_JID_TYPE);
            sjvl.send(future, jidJid1);
            LongJid rpa = (LongJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetLong.req.send(future, rpa);
            assertEquals(0L, v);
            (new SetLong(-1000000000000L)).send(future, rpa);
            rpa = (LongJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetLong.req.send(future, rpa);
            assertEquals(-1000000000000L, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
