package org.agilewiki.jid.jid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.requests.GetSerializedLength;

public class JidTest extends TestCase {
    public void test1() {
        System.err.println("\nTest 1");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            future.send(a, new Include(JID.class));
            int l = (Integer) future.send(a, new GetSerializedLength());
            System.err.println(l);
            assertEquals(l, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
    public void test2() {
        System.err.println("\nTest 2");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailbox);
            future.send(factory, new Include(JidFactories.class));

            JCActor jid = new JCActor(mailbox);
            future.send(jid, new Include(JID.class));
            int l = (Integer) future.send(jid, new GetSerializedLength());
            System.err.println(l);
            assertEquals(l, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
