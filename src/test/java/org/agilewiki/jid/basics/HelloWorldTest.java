package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class HelloWorldTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        factory.defineActorType("hi", HelloWorld.class);

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);
        (new SetActor("hi")).send(future, root);
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        RootJid root2 = new RootJid();
        root2.initialize(mailbox, factory);
        root2.load(rootBytes);
        Actor a = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, a);

        mailboxFactory.close();
    }
}
