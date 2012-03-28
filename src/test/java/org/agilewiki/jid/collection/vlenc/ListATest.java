package org.agilewiki.jid.collection.vlenc;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.ISetBytes;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.string.SetString;

public class ListATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            (new Include(StringActorsType.class)).call(factory);
            Open.req.call(factory);
            JAFuture future = new JAFuture();
            NewJID newListJid = new NewJID(JidFactories.LIST_JID_ATYPE);
            Actor l0 = newListJid.send(future, factory).thisActor();
            int l0sl = GetSerializedLength.req.send(future, l0);
            assertEquals(8, l0sl);
            Actor l1 = (new CopyJID()).send(future, l0);
            int l1sl = GetSerializedLength.req.send(future, l1);
            assertEquals(8, l1sl);
            (new IAdd(0)).send(future, l1);
            l1sl = GetSerializedLength.req.send(future, l1);
            assertEquals(12, l1sl);
            Actor l2 = (new CopyJID()).send(future, l1);
            int l2sl = GetSerializedLength.req.send(future, l2);
            assertEquals(12, l2sl);
            NewJID newStringJid = new NewJID(JidFactories.STRING_JID_TYPE);
            Actor s0 = newStringJid.send(future, factory).thisActor();
            (new SetString("Hi")).send(future, s0);
            int s0sl = GetSerializedLength.req.send(future, s0);
            assertEquals(8, s0sl);
            byte[] s0bs = GetSerializedBytes.req.send(future, s0);
            assertEquals(8, s0bs.length);
            (new IAddBytes(-1, s0bs)).send(future, l2);
            l2sl = GetSerializedLength.req.send(future, l2);
            assertEquals(20, l2sl);
            (new ISetBytes(0, s0bs)).send(future, l2);
            l2sl = GetSerializedLength.req.send(future, l2);
            assertEquals(24, l2sl);
            (new IRemove(0)).send(future, l2);
            l2sl = GetSerializedLength.req.send(future, l2);
            assertEquals(16, l2sl);
            Empty.req.send(future, l2);
            l2sl = GetSerializedLength.req.send(future, l2);
            assertEquals(8, l2sl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
