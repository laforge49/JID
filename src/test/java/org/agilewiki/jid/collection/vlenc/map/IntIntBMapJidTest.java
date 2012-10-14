package org.agilewiki.jid.collection.vlenc.map;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;

public class IntIntBMapJidTest extends TestCase {
    public void test1() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            m.kMake(0);
            m.kMake(1);
            m.kMake(2);
            IntegerJid sj0 = m.kGet(0);
            IntegerJid sj1 = m.kGet(1);
            IntegerJid sj2 = m.kGet(2);
            sj0.setValue(0);
            sj1.setValue(1);
            sj2.setValue(2);
            BMapJid<Integer, IntegerJid> n = (BMapJid) m.copyJID(mailbox);
            IntegerJid s0 = n.kGet(0);
            IntegerJid s1 = n.kGet(1);
            IntegerJid s2 = n.kGet(2);
            assertEquals(0, (int) s0.getValue());
            assertEquals(1, (int) s1.getValue());
            assertEquals(2, (int) s2.getValue());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test2() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 28) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            assertEquals(28, m.size());
            i = 0;
            while (i < 28) {
                IntegerJid ij = m.kGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } finally {
            mailboxFactory.close();
        }
    }

    public void test3() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 41) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 41) {
                IntegerJid ij = m.kGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
            assertEquals(41, m.size());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test4() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 391) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            assertEquals(391, m.size());
            i = 0;
            while (i < 391) {
                IntegerJid ij = m.kGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } finally {
            mailboxFactory.close();
        }
    }

    public void test5() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 10000) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                IntegerJid ij = m.kGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
            assertEquals(10000, m.size());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test6() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 10000) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                m.iRemove(0);
                i += 1;
            }
            assertTrue(m.isLeaf());
            assertEquals(0, m.size());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test7() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 10000) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                m.iRemove(-1);
                i += 1;
            }
            assertTrue(m.isLeaf());
            assertEquals(0, m.size());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test8() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 10000) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                m.kRemove(i);
                i += 1;
            }
            assertTrue(m.isLeaf());
            assertEquals(0, m.size());
        } finally {
            mailboxFactory.close();
        }
    }

    public void test9() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < 10000) {
                m.kMake(i);
                IntegerJid ij0 = m.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                m.kRemove(9999 - i);
                i += 1;
            }
            assertTrue(m.isLeaf());
            assertEquals(0, m.size());
        } finally {
            mailboxFactory.close();
        }
    }
}
