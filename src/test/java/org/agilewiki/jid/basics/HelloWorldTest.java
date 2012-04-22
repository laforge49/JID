package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jactor.factory.NewActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class HelloWorldTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory(mailbox);
        factory.defineActorType("root", RootJid.class);
        factory.defineActorType("hi", HelloWorld.class);

        RootJid root = (RootJid) (new NewActor("root")).call(factory);
        (new SetActor("hi")).send(future, root);
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        ReadableBytes rb = new ReadableBytes(rootBytes, 0);
        RootJid root2 = (RootJid) (new NewActor("root")).call(factory);
        root2.load(rb);
        Actor a = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, a);

        mailboxFactory.close();
    }
}
