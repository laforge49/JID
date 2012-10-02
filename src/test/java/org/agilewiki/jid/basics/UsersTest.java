package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.collection.vlenc.map.KGet;
import org.agilewiki.jid.collection.vlenc.map.KMake;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class UsersTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        factory.registerActorFactory(new UsersFactory("users"));
        JidFactories factories = new JidFactories();
        factories.initialize(mailbox, factory);

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);
        (new SetActor("users")).send(future, root);
        Users users = (Users) (new ResolvePathname("0")).send(future, root);
        new KMake<String, User>("John").send(future, users);
        StringJid jEmail = (StringJid) new KGet<String, User>("John").send(future, users);
        jEmail.setValue("john123@gmail.com");
        new KMake<String, User>("Sam").send(future, users);
        StringJid sEmail = (StringJid) new KGet<String, User>("Sam").send(future, users);
        sEmail.setValue("sammyjr@yahoo.com");
        new KMake<String, User>("Fred").send(future, users);
        StringJid fEmail = (StringJid) new KGet<String, User>("Fred").send(future, users);
        fEmail.setValue("fredk@gmail.com");
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        RootJid root2 = new RootJid();
        root2.initialize(mailbox, factory);
        root2.load(rootBytes);
        Actor a = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, a);

        mailboxFactory.close();
    }
}
