package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.flens.integer.SetInteger;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class LuckyNumberTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        factory.defineActorType("lucky number", LuckyNumber.class);

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);
        (new SetActor("lucky number")).send(future, root);
        Actor a = (new ResolvePathname("0")).send(future, root);
        (new SetInteger(7)).send(future, a);
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        RootJid root2 = new RootJid();
        root2.initialize(mailbox, factory);
        root2.load(rootBytes);
        Actor a2 = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, a2);

        mailboxFactory.close();
    }
}
