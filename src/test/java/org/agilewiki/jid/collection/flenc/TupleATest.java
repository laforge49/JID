package org.agilewiki.jid.collection.flenc;

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
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.ISetBytes;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.string.GetString;
import org.agilewiki.jid.scalar.vlens.string.SetString;

public class TupleATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            (new Include(StringStringTupleC.class)).call(factory);
            Open.req.call(factory);
            NewJID newTupleJid = new NewJID(JidFactories.TUPLE_JID_ATYPE);
            Actor t0 = newTupleJid.send(future, factory).thisActor();
            IGet iget0 = new IGet(0);
            IGet iget1 = new IGet(1);
            Actor e0 = iget0.send(future, t0);
            assertNull(GetString.req.send(future, e0));
            Actor e1 = iget1.send(future, t0);
            assertNull(GetString.req.send(future, e1));
            (new SetString("Apples")).send(future, e0);
            assertEquals("Apples", GetString.req.send(future, e0));
            (new SetString("Oranges")).send(future, e1);
            assertEquals("Oranges", GetString.req.send(future, e1));
            Actor t1 = (new CopyJID()).send(future, t0);
            Actor f0 = (new ResolvePathname("0")).send(future, t1);
            assertEquals("Apples", GetString.req.send(future, f0));
            Actor f1 = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Oranges", GetString.req.send(future, f1));

            NewJID newStringJid = new NewJID(JidFactories.STRING_JID_CTYPE);
            Actor string1 = newStringJid.send(future, factory).thisActor();
            (new SetString("Peaches")).send(future, string1);
            byte[] sb = GetSerializedBytes.req.send(future, string1);
            (new ISetBytes(1, sb)).send(future, t1);
            Actor f1b = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Peaches", GetString.req.send(future, f1b));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
