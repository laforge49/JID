package org.agilewiki.jid.scalar.flens.bool;

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
import org.agilewiki.jid.scalar.flens.SetBoolean;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJidC;

public class BooleanATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newBooleanJid = new NewJID(JidFactories.BOOLEAN_JID_ATYPE);
            BooleanJidA boolean1 = (BooleanJidA) newBooleanJid.send(future, factory).thisActor();
            BooleanJidA boolean2 = (BooleanJidA) (new CopyJID()).send(future, boolean1);
            (new SetBoolean(true)).send(future, boolean2);
            BooleanJidA boolean3 = (BooleanJidA) (new CopyJID()).send(future, boolean2);

            int sl = GetSerializedLength.req.send(future, boolean1);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean2);
            assertEquals(1, sl);
            sl = GetSerializedLength.req.send(future, boolean3);
            assertEquals(1, sl);

            assertFalse(GetBoolean.req.send(future, boolean1));
            assertTrue(GetBoolean.req.send(future, boolean2));
            assertTrue(GetBoolean.req.send(future, boolean3));

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvb = JidJidC.setValueReq(JidFactories.BOOLEAN_JID_ATYPE);
            sjvb.send(future, jidJid1);
            BooleanJidA rpa = (BooleanJidA) (new ResolvePathname("0")).send(future, jidJid1);
            assertFalse(GetBoolean.req.send(future, rpa));
            (new SetBoolean(true)).send(future, rpa);
            rpa = (BooleanJidA) (new ResolvePathname("0")).send(future, jidJid1);
            assertTrue(GetBoolean.req.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
