package org.agilewiki.jid.timings.list;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;

public class BigListTest extends TestCase {
    public void test() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories(mailboxFactory.createMailbox());
        factory.setParent(null);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);

        //NewJID newList = new NewJID(JidFactories.)

        mailboxFactory.close();
    }
}
