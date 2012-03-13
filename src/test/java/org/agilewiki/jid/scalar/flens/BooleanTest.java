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
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJid;

public class BooleanTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newBooleanJid = new NewJID(JidFactories.BOOLEAN_JID_CTYPE);
            Actor boolean1 = newBooleanJid.send(future, factory).thisActor();
            Actor boolean2 = (new CopyJID()).send(future, boolean1);
            BooleanJid.setValueReq(true).send(future, boolean2);
            Actor boolean3 = (new CopyJID()).send(future, boolean2);

            int sl = GetSerializedLength.req.send(future, boolean1);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean2);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean3);
            assertEquals(1, sl);

            assertFalse(BooleanJid.getValueReq.send(future, boolean1));
            assertTrue(BooleanJid.getValueReq.send(future, boolean2));
            assertTrue(BooleanJid.getValueReq.send(future, boolean3));

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvb = JidJid.setValueReq(JidFactories.BOOLEAN_JID_CTYPE);
            sjvb.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertFalse(BooleanJid.getValueReq.send(future, rpa));
            BooleanJid.setValueReq(true).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertTrue(BooleanJid.getValueReq.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
