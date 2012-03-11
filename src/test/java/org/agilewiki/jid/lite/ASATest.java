package org.agilewiki.jid.lite;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jid.LiteActor;

/**
 * Test code.
 */
public class ASATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Actor a = new A(mailboxFactory.createAsyncMailbox());
            Actor s1 = new S(mailboxFactory.createMailbox(), a);
            Actor s2 = new S(mailboxFactory.createAsyncMailbox(), s1);
            JAFuture future = new JAFuture();
            System.err.println(future.send(s2, null));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    class S extends LiteActor {
        Actor n;

        S(Mailbox mailbox, Actor n) {
            super(mailbox);
            this.n = n;
        }

        @Override
        public void processRequest(Object request, RP rp) throws Exception {
            System.err.println("S got request");
            send(n, request, rp);
        }
    }

    class A extends LiteActor {

        A(Mailbox mailbox) {
            super(mailbox);
        }

        @Override
        public void processRequest(Object request, RP rp) throws Exception {
            System.err.println("A got request");
            rp.processResponse(request);
        }
    }
}
