package org.agilewiki.jid.collection.flenc;

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
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.ISetBytes;
import org.agilewiki.jid.requests.CopyJID;
import org.agilewiki.jid.requests.GetBytes;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.StringJid;

public class TupleTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            (new Include(StringStringTuple.class)).call(factory);
            Open.req.call(factory);
            NewActor newTupleJid = new NewActor(JidFactories.TUPLE_JID_TYPE);
            JCActor t0 = newTupleJid.send(future, factory);
            Open.req.call(t0);
            IGet iget0 = new IGet(0);
            IGet iget1 = new IGet(1);
            Actor e0 = iget0.send(future, t0);
            assertNull(StringJid.getValueReq.send(future, e0));
            Actor e1 = iget1.send(future, t0);
            assertNull(StringJid.getValueReq.send(future, e1));
            StringJid.setValueReq("Apples").send(future, e0);
            assertEquals("Apples", StringJid.getValueReq.send(future, e0));
            StringJid.setValueReq("Oranges").send(future, e1);
            assertEquals("Oranges", StringJid.getValueReq.send(future, e1));
            Actor t1 = (new CopyJID()).send(future, t0);
            Actor f0 = (new ResolvePathname("0")).send(future, t1);
            assertEquals("Apples", StringJid.getValueReq.send(future, f0));
            Actor f1 = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Oranges", StringJid.getValueReq.send(future, f1));

            NewActor newStringJid = new NewActor(JidFactories.STRING_JID_TYPE);
            JCActor string1 = newStringJid.send(future, factory);
            Open.req.call(string1);
            StringJid.setValueReq("Peaches").send(future, string1);
            byte[] sb = GetBytes.req.send(future, string1);
            (new ISetBytes(1, sb)).send(future, t1);
            Actor f1b = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Peaches", StringJid.getValueReq.send(future, f1b));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
