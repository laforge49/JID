package org.agilewiki.jid.scalar.flens;

import junit.framework.TestCase;
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

public class DoubleTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewActor newDoubleJid = new NewActor(JidFactories.DOUBLE_JID_TYPE);
            JCActor double1 = newDoubleJid.send(future, factory);
            Open.req.call(double1);
            JCActor double2 = (new CopyJID()).send(future, double1);
            Open.req.call(double2);
            DoubleJid.setValueReq(1.D).send(future, double2);
            JCActor double3 = (new CopyJID()).send(future, double2);
            Open.req.call(double3);

            int sl = GetSerializedLength.req.send(future, double1);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double2);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double3);
            assertEquals(8, sl);

            double v = DoubleJid.getValueReq.send(future, double1);
            assertEquals(0.D, v);
            v = DoubleJid.getValueReq.send(future, double2);
            assertEquals(1.D, v);
            v = DoubleJid.getValueReq.send(future, double3);
            assertEquals(1.D, v);

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            Open.req.call(jidJid1);
            SetValue sjvl = JidJid.setValueReq(JidFactories.DOUBLE_JID_TYPE);
            sjvl.send(future, jidJid1);
            JCActor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = DoubleJid.getValueReq.send(future, rpa);
            assertEquals(0.D, v);
            DoubleJid.setValueReq(-1.D).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = DoubleJid.getValueReq.send(future, rpa);
            assertEquals(-1.D, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
