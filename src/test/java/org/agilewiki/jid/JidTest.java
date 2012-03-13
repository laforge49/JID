package org.agilewiki.jid;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.jidFactory.NewJID;

public class JidTest extends TestCase {
    public void test1() {
        System.err.println("\nTest 1");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidC.class)).call(a);
            Open.req.call(a);
            int l = GetSerializedLength.req.send(future, a);
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
            JCActor jidFactory = new JCActor(mailbox);
            (new Include(JidFactories.class)).call(jidFactory);
            Open.req.call(jidFactory);

            Actor jid = (new NewJID(JidFactories.JID_CTYPE)).call(jidFactory).thisActor();
            int l = GetSerializedLength.req.send(future, jid);
            System.err.println(l);
            assertEquals(l, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test3() {
        System.err.println("\nTest 3");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidC.class)).call(a);
            Open.req.call(a);
            int l = GetSerializedLength.req.send(future, a);
            AppendableBytes appendableBytes = new AppendableBytes(l);
            (new Save(appendableBytes)).send(future, a);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test4() {
        System.err.println("\nTest 4");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidC.class)).call(a);
            Open.req.call(a);
            byte[] bytes = GetBytes.req.send(future, a);
            int l = bytes.length;
            System.err.println(l);
            assertEquals(l, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test5() {
        System.err.println("\nTest 5");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFuture future = new JAFuture();
            JCActor jidFactory = new JCActor(mailbox);
            (new Include(JidFactories.class)).call(jidFactory);
            Open.req.call(jidFactory);

            Actor jid = (new NewJID(JidFactories.JID_CTYPE, new byte[0])).call(jidFactory).thisActor();
            int l = GetSerializedLength.req.send(future, jid);
            System.err.println(l);
            assertEquals(l, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test6() {
        System.err.println("\nTest 6");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFuture future = new JAFuture();
            JCActor jidFactory = new JCActor(mailbox);
            (new Include(JidFactories.class)).call(jidFactory);
            Open.req.call(jidFactory);

            Actor jid1 = (new NewJID(JidFactories.JID_CTYPE, new byte[0])).call(jidFactory).thisActor();
            Actor jid2 = (new CopyJID(mailbox)).send(future, jid1);
            int l = GetSerializedLength.req.send(future, jid2);
            System.err.println(l);
            assertEquals(l, 0);
            boolean eq = (new IsJidEqual(jid2)).send(future, jid1);
            assertTrue(eq);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test7() {
        System.err.println("\nTest 7");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidC.class)).call(a);
            Open.req.call(a);
            JCActor b = (JCActor) future.send(a, new ResolvePathname(""));
            assertEquals(a, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
