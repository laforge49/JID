package org.agilewiki.jid.scalar.flens;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJidC;

public class DoubleTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newDoubleJid = new NewJID(JidFactories.DOUBLE_JID_CTYPE);
            Actor double1 = newDoubleJid.send(future, factory).thisActor();
            Actor double2 = (new CopyJID()).send(future, double1);
            DoubleJidC.setValueReq(1.D).send(future, double2);
            Actor double3 = (new CopyJID()).send(future, double2);

            int sl = GetSerializedLength.req.send(future, double1);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double2);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double3);
            assertEquals(8, sl);

            double v = DoubleJidC.getValueReq.send(future, double1);
            assertEquals(0.D, v);
            v = DoubleJidC.getValueReq.send(future, double2);
            assertEquals(1.D, v);
            v = DoubleJidC.getValueReq.send(future, double3);
            assertEquals(1.D, v);

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvl = JidJidC.setValueReq(JidFactories.DOUBLE_JID_CTYPE);
            sjvl.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = DoubleJidC.getValueReq.send(future, rpa);
            assertEquals(0.D, v);
            DoubleJidC.setValueReq(-1.D).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = DoubleJidC.getValueReq.send(future, rpa);
            assertEquals(-1.D, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
