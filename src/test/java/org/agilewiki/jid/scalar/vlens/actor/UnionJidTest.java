package org.agilewiki.jid.scalar.vlens.actor;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJid;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class UnionJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            UnionJidFactory siuf = new UnionJidFactory("siUnion", StringJidFactory.fac, IntegerJidFactory.fac);
            factory.registerActorFactory(siuf);
            UnionJid siu1 = (UnionJid) factory.newActor("siUnion");
            assertNull(siu1.getValue());
            UnionJid siu2 = (UnionJid) siu1.copyJID(mailbox);
            assertNull(siu2.getValue());
            siu2.setValue(StringJidFactory.fac);
            StringJid sj2 = (StringJid) siu2.getValue();
            assertNotNull(sj2);
            UnionJid siu3 = (UnionJid) siu2.copyJID(mailbox);
            StringJid sj3 = (StringJid) siu3.getValue();
            assertNotNull(sj3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
