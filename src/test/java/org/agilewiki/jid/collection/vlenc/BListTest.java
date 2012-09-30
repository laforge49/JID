package org.agilewiki.jid.collection.vlenc;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJid;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class BListTest extends TestCase {
    public void test1() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory stringListFactory = new BListJidFactory("sl", StringJidFactory.fac);
            factory.registerActorFactory(stringListFactory);
            BListJid stringList1 = (BListJid) factory.newActor("sl");
            stringList1.iAdd(0);
            stringList1.iAdd(1);
            stringList1.iAdd(2);
            StringJid sj0 = (StringJid) stringList1.iGet(0);
            StringJid sj1 = (StringJid) stringList1.iGet(1);
            StringJid sj2 = (StringJid) stringList1.iGet(2);
            sj0.setValue("a");
            sj1.setValue("b");
            sj2.setValue("c");
            BListJid stringList2 = (BListJid) stringList1.copyJID(mailbox);
            StringJid s0 = (StringJid) stringList2.iGet(0);
            StringJid s1 = (StringJid) stringList2.iGet(1);
            StringJid s2 = (StringJid) stringList2.iGet(2);
            assertEquals("a", s0.getValue());
            assertEquals("b", s1.getValue());
            assertEquals("c", s2.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test2() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 28) {
                intList1.iAdd(i);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(i);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 28) {
                IntegerJid ij = (IntegerJid) intList1.iGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test3() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 41) {
                intList1.iAdd(-1);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(-1);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 41) {
                IntegerJid ij = (IntegerJid) intList1.iGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test4() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 391) {
                intList1.iAdd(-1);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(-1);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 391) {
                IntegerJid ij = (IntegerJid) intList1.iGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test5() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 10000) {
                intList1.iAdd(-1);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(-1);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                IntegerJid ij = (IntegerJid) intList1.iGet(i);
                assertEquals(i, (int) ij.getValue());
                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test6() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 10000) {
                intList1.iAdd(-1);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(-1);
                ij0.setValue(i);
                i += 1;
            }
            i = 0;
            while (i < 10000) {
                intList1.iRemove(0);
                i += 1;
            }
            assertEquals(0, intList1.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
