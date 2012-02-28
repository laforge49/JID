package org.agilewiki.jid.scalar.flen;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.requests.CopyJID;
import org.agilewiki.jid.requests.GetSerializedLength;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlen.JidJid;

public class BooleanTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);

            NewActor newBooleanJid = new NewActor(JidFactories.BOOLEAN_JID_TYPE);
            JCActor boolean1 = newBooleanJid.send(future, factory);
            JCActor boolean2 = (new CopyJID()).send(future, boolean1);
            BooleanJid.setValueReq(true).send(future, boolean2);
            JCActor boolean3 = (new CopyJID()).send(future, boolean2);

            int sl = GetSerializedLength.req.send(future, boolean1);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean2);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean3);
            assertEquals(1, sl);

            assertFalse(BooleanJid.getValueReq.send(future, boolean1));
            assertTrue(BooleanJid.getValueReq.send(future, boolean2));
            assertTrue(BooleanJid.getValueReq.send(future, boolean3));

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            SetValue sjvb = JidJid.setValueReq(JidFactories.BOOLEAN_JID_TYPE);
            sjvb.send(future, jidJid1);
            JCActor rpa = (new ResolvePathname("$")).send(future, jidJid1);
            assertFalse(BooleanJid.getValueReq.send(future, rpa));
            BooleanJid.setValueReq(true).send(future, rpa);
            rpa = (new ResolvePathname("$")).send(future, jidJid1);
            assertTrue(BooleanJid.getValueReq.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
