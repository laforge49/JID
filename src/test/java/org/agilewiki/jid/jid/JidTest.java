package org.agilewiki.jid.jid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.requests.GetSerializedLength;

public class JidTest extends TestCase {
    public void test() {
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
}
