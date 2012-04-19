package org.agilewiki.jid.scalar.vlens.bytes;

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
import org.agilewiki.jid.scalar.vlens.Clear;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class BytesTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            Actor factory = new JidFactories(mailboxFactory.createMailbox());
            factory.setParent(null);

            NewJID newBytesJid = new NewJID(JidFactories.BYTES_JID_TYPE, factory.getMailbox(), factory);
            Actor bytes1 = newBytesJid.send(future, factory).thisActor();
            Actor bytes2 = (new CopyJID()).send(future, bytes1);
            (new SetBytes(new byte[3])).send(future, bytes2);
            Actor bytes3 = (new CopyJID()).send(future, bytes2);

            int sl = GetSerializedLength.req.send(future, bytes1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, bytes2);
            assertEquals(7, sl);
            sl = GetSerializedLength.req.send(future, bytes3);
            assertEquals(7, sl);

            assertNull(GetBytes.req.send(future, bytes1));
            assertEquals(3, GetBytes.req.send(future, bytes2).length);
            assertEquals(3, GetBytes.req.send(future, bytes3).length);

            Actor jidJid1 = (new ActorJidFactory()).newActor(factory.getMailbox(), factory);
            SetActor sjvbs = new SetActor(JidFactories.BYTES_JID_TYPE);
            sjvbs.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertNull(GetBytes.req.send(future, rpa));
            assertTrue((new MakeBytes(new byte[0])).send(future, rpa));
            assertFalse((new MakeBytes(new byte[99])).send(future, rpa));
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertEquals(0, GetBytes.req.send(future, rpa).length);
            Clear.req.sendEvent(rpa);
            assertNull(GetBytes.req.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
