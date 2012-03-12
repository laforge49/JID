package org.agilewiki.jid.scalar.vlens;

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

public class BytesTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewActor newBytesJid = new NewActor(JidFactories.BYTES_JID_TYPE);
            JCActor bytes1 = (JCActor) newBytesJid.send(future, factory);
            Open.req.call(bytes1);
            Actor bytes2 = (new CopyJID()).send(future, bytes1);
            BytesJid.setValueReq(new byte[3]).send(future, bytes2);
            Actor bytes3 = (new CopyJID()).send(future, bytes2);

            int sl = GetSerializedLength.req.send(future, bytes1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, bytes2);
            assertEquals(7, sl);
            sl = GetSerializedLength.req.send(future, bytes3);
            assertEquals(7, sl);

            assertNull(BytesJid.getValueReq.send(future, bytes1));
            assertEquals(3, BytesJid.getValueReq.send(future, bytes2).length);
            assertEquals(3, BytesJid.getValueReq.send(future, bytes3).length);

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = (JCActor) newJidJid.send(future, factory);
            Open.req.call(jidJid1);
            SetValue sjvbs = JidJid.setValueReq(JidFactories.BYTES_JID_TYPE);
            sjvbs.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertNull(BytesJid.getValueReq.send(future, rpa));
            assertTrue(BytesJid.makeValueReq(new byte[0]).send(future, rpa));
            assertFalse(BytesJid.makeValueReq(new byte[99]).send(future, rpa));
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertEquals(0, BytesJid.getValueReq.send(future, rpa).length);
            Clear.req.sendEvent(rpa);
            assertNull(BytesJid.getValueReq.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
