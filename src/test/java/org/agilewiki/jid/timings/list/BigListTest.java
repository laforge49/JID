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

    public void test2() throws Exception {
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

        BooleanAppender ba = new BooleanAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 10000;
        //Appends per second = 28,918,449

        ba.list = list;
        TimeBooleanAppender.req.send(future, ba);
        long t = TimeBooleanAppender.req.send(future, ba);
        System.out.println("list size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("appends per second = " + ips);
        }

        mailboxFactory.close();
    }

    public void test3() throws Exception {
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

        BooleanAppender ba = new BooleanAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 10000;
        //Appends per second = 31,796,502

        list.initialCapacity = ba.count;

        ba.list = list;
        TimeBooleanAppender.req.send(future, ba);
        long t = TimeBooleanAppender.req.send(future, ba);
        System.out.println("list size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("appends per second = " + ips);
        }

        mailboxFactory.close();
    }

    public void test4() throws Exception {
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

        BooleanAppender ba = new BooleanAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 10000;
        //Appends and serializes per second = 26,164,311

        list.initialCapacity = ba.count;

        ba.list = list;
        TimeBooleanAppender.req.send(future, ba);
        long t = TimeBooleanSAppender.req.send(future, ba);
        System.out.println("list size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("appends and serializes per second = " + ips);
        }

        mailboxFactory.close();
    }
}
