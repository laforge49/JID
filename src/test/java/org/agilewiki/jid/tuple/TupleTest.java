package org.agilewiki.jid.tuple;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.vlen.StringJid;

public class TupleTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            (new Include(StringStringTuple.class)).call(factory);
            Open.req.call(factory);
            JCActor t0 = new JCActor(mailboxFactory.createMailbox());
            t0.setParent(factory);
            (new Include(TupleJid.class)).call(t0);
            Open.req.call(t0);
            IGet iget0 = new IGet(0);
            IGet iget1 = new IGet(1);
            JCActor e0 = iget0.send(future, t0);
            assertNull(StringJid.getValueReq.send(future, e0));
            JCActor e1 = iget0.send(future, t0);
            assertNull(StringJid.getValueReq.send(future, e1));
            StringJid.setValueReq("Apples").send(future, e0);
            assertEquals("Apples", StringJid.getValueReq.send(future, e0));
            StringJid.setValueReq("Oranges").send(future, e1);
            assertEquals("Oranges", StringJid.getValueReq.send(future, e1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
