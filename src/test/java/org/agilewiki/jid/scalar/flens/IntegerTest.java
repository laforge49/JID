package org.agilewiki.jid.scalar.flens;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.requests.CopyJID;
import org.agilewiki.jid.requests.GetSerializedLength;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJid;

public class IntegerTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewActor newIntegerJid = new NewActor(JidFactories.INTEGER_JID_TYPE);
            JCActor int1 = newIntegerJid.send(future, factory);
            Open.req.call(int1);
            Actor int2 = (new CopyJID()).send(future, int1);
            IntegerJid.setValueReq(1).send(future, int2);
            Actor int3 = (new CopyJID()).send(future, int2);

            int sl = GetSerializedLength.req.send(future, int1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, int2);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, int3);
            assertEquals(4, sl);

            int v = IntegerJid.getValueReq.send(future, int1);
            assertEquals(0, v);
            v = IntegerJid.getValueReq.send(future, int2);
            assertEquals(1, v);
            v = IntegerJid.getValueReq.send(future, int3);
            assertEquals(1, v);

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            Open.req.call(jidJid1);
            SetValue sjvi = JidJid.setValueReq(JidFactories.INTEGER_JID_TYPE);
            sjvi.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = IntegerJid.getValueReq.send(future, rpa);
            assertEquals(0, v);
            IntegerJid.setValueReq(-1).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = IntegerJid.getValueReq.send(future, rpa);
            assertEquals(-1, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
