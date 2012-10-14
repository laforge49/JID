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
}
