package org.agilewiki.jid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;

public class JidTest extends TestCase {
    public void test1() {
        System.err.println("\nTest 1");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            Jid a = new Jid();
            a.initialize(mailboxFactory.createMailbox());
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
            JAFuture future = new JAFuture();
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());

            Jid jid = (Jid) JidFactory.fac.newActor(factory.getMailbox(), factory);
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
            Jid a = new Jid();
            a.initialize(mailboxFactory.createMailbox());
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
            Jid a = new Jid();
            a.initialize(mailboxFactory.createMailbox());
            byte[] bytes = GetSerializedBytes.req.send(future, a);
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
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());

            Jid jid = (Jid) JidFactory.fac.newActor(factory.getMailbox(), factory);
            jid.load(new ReadableBytes(new byte[0], 0));
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
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());

            Jid jid1 = (Jid) JidFactory.fac.newActor(factory.getMailbox(), factory);
            jid1.load(new ReadableBytes(new byte[0], 0));
            Jid jid2 = (Jid) (new CopyJID(mailbox)).send(future, jid1);
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
            Jid a = new Jid();
            a.initialize(mailboxFactory.createMailbox());
            Jid b = (Jid) future.send(a, new ResolvePathname(""));
            assertEquals(a, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
