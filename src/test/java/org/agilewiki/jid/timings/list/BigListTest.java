package org.agilewiki.jid.timings.list;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.BooleanListJid;
import org.agilewiki.jid.collection.vlenc.BooleanListJidFactory;
import org.agilewiki.jid.collection.vlenc.IAdd;
import org.agilewiki.jid.scalar.vlens.actor.GetActor;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class BigListTest extends TestCase {
    public void test1() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories(mailboxFactory.createMailbox());
        factory.setParent(null);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);

        SetActor setList = new SetActor(new BooleanListJidFactory());
        setList.send(future, root);
        BooleanListJid list = (BooleanListJid) GetActor.req.send(future, root);

        IAdd iAdd = new IAdd(-1);
        int i = 0;
        while (i < 100) {
            iAdd.send(future, list);
            i += 1;
        }

        mailboxFactory.close();
    }
}
