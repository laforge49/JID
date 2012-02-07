package org.agilewiki.jid.jid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.requests.*;

public class JidTest extends TestCase {
    public void test1() {
        System.err.println("\nTest 1");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor a = new JCActor(mailboxFactory.createMailbox());
            (new Include(JID.class)).call(a);
            int l = (new GetSerializedLength()).send(future, a);
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

            JCActor jid = (JCActor) future.send(jidFactory, new NewActor(JidFactories.JID_NAME));
            int l = (new GetSerializedLength()).send(future, jid);
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
            (new Include(JID.class)).call(a);
            int l = (new GetSerializedLength()).send(future, a);
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
            (new Include(JID.class)).call(a);
            byte[] bytes = (new GetBytes()).send(future, a);
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

            JCActor jid = (new NewJID(JidFactories.JID_NAME, new byte[0])).call(jidFactory);
            int l = (new GetSerializedLength()).send(future, jid);
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

            JCActor jid1 = (new NewJID(JidFactories.JID_NAME, new byte[0])).call(jidFactory);
            JCActor jid2 = (new CopyJID(mailbox)).send(future, jid1);
            int l = (new GetSerializedLength()).send(future, jid2);
            System.err.println(l);
            assertEquals(l, 0);
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
            (new Include(JID.class)).call(a);
            JCActor b = (JCActor) future.send(a, new ResolvePathname(""));
            assertEquals(a, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
