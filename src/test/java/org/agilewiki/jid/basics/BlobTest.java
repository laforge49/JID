package org.agilewiki.jid.basics;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.actor.ActorJid;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class BlobTest extends TestCase {
    public void test()
            throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();
        JAFactory factory = new JAFactory(mailbox);
        factory.registerActorFactory(new BlobFactory("blob"));
        factory.defineActorType("hi", HelloWorld.class);
        JidFactories factories = new JidFactories(mailbox);
        factories.setParent(factory);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);
        (new SetActor("blob")).send(future, root);
        Blob blob = (Blob) (new ResolvePathname("0")).send(future, root);
        blob.newKMake("fun").send(future, blob);
        ActorJid fun = (ActorJid) blob.newKGet("fun").send(future, blob);
        (new SetActor("hi")).send(future, fun);
        byte[] rootBytes = GetSerializedBytes.req.send(future, root);

        RootJid root2 = new RootJid(mailbox);
        root2.setParent(factory);
        root2.load(rootBytes);
        Actor a = (new ResolvePathname("0")).send(future, root2);
        Proc.req.send(future, a);

        mailboxFactory.close();
    }
}
