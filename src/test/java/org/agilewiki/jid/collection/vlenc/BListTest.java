package org.agilewiki.jid.collection.vlenc;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
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
            JAFuture future = new JAFuture();
            BListJidFactory stringListFactory = new BListJidFactory("slf", StringJidFactory.fac);
            factory.registerActorFactory(stringListFactory);
            BListJid stringList1 = (BListJid) factory.newActor("slf");
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
}
