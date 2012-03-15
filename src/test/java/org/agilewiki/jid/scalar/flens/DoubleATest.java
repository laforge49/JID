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

public class DoubleATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newDoubleJid = new NewJID(JidFactories.DOUBLE_JID_ATYPE);
            DoubleJidA double1 = (DoubleJidA) newDoubleJid.send(future, factory).thisActor();
            DoubleJidA double2 = (DoubleJidA) (new CopyJID()).send(future, double1);
            DoubleJidA.setValueReq(1.D).send(future, double2);
            DoubleJidA double3 = (DoubleJidA) (new CopyJID()).send(future, double2);

            int sl = GetSerializedLength.req.send(future, double1);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double2);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, double3);
            assertEquals(8, sl);

            double v = GetDouble.req.send(future, double1);
            assertEquals(0.D, v);
            v = GetDouble.req.send(future, double2);
            assertEquals(1.D, v);
            v = GetDouble.req.send(future, double3);
            assertEquals(1.D, v);

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvl = JidJidC.setValueReq(JidFactories.DOUBLE_JID_ATYPE);
            sjvl.send(future, jidJid1);
            DoubleJidA rpa = (DoubleJidA) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetDouble.req.send(future, rpa);
            assertEquals(0.D, v);
            DoubleJidA.setValueReq(-1.D).send(future, rpa);
            rpa = (DoubleJidA) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetDouble.req.send(future, rpa);
            assertEquals(-1.D, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
