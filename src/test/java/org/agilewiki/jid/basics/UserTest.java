package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.flens.integer.SetInteger;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;
import org.agilewiki.jid.scalar.vlens.string.SetString;

public class UserTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        factory.registerActorFactory(new UserFactory("user"));
        JidFactories factories = new JidFactories();
        factories.initialize(mailbox, factory);

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);
        (new SetActor("user")).send(future, root);
        Actor name = (new ResolvePathname("0/0")).send(future, root);
        (new SetString("Frank")).send(future, name);
        Actor age = (new ResolvePathname("0/1")).send(future, root);
        (new SetInteger(30)).send(future, age);
        Actor location = (new ResolvePathname("0/2")).send(future, root);
        (new SetString("Bangalore")).send(future, location);
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        RootJid root2 = new RootJid();
        root2.initialize(mailbox, factory);
        root2.load(rootBytes);
        Actor user = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, user);

        mailboxFactory.close();
    }
}
