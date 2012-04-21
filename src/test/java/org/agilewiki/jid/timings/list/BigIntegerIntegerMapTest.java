package org.agilewiki.jid.timings.list;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.map.integer.IntegerIntegerMapJid;
import org.agilewiki.jid.collection.vlenc.map.integer.IntegerIntegerMapJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.GetActor;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class BigIntegerIntegerMapTest extends TestCase {
    public void test1() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories(mailboxFactory.createMailbox());
        factory.setParent(null);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        int i = 0;
        while (i < 10) {
            map.newKMake(i).send(future, map);
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

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        MapAppender ba = new MapAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 5000;
        //ba.repeat = 1000;
        //Appends per second = 2,079,002

        ba.map = map;
        TimeMapAppender.req.send(future, ba);
        long t = TimeMapAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
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

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        MapAppender ba = new MapAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 1000;
        //Appends per second = 2897710

        map.initialCapacity = ba.count;

        ba.map = map;
        TimeMapAppender.req.send(future, ba);
        long t = TimeMapAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
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

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        MapSAppender ba = new MapSAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 1000;
        //Appends and serializes per second = 1,080,030

        map.initialCapacity = ba.count;

        ba.map = map;
        TimeMapSAppender.req.send(future, ba);
        long t = TimeMapSAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("appends and serializes per second = " + ips);
        }

        mailboxFactory.close();
    }

    public void test5() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories(mailboxFactory.createMailbox());
        factory.setParent(null);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        MapDAppender ba = new MapDAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 10000;
        //Deserializes per second = 12,500,000,000

        map.initialCapacity = ba.count;

        ba.map = map;
        TimeMapDAppender.req.send(future, ba);
        long t = TimeMapDAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("Deserializes per second = " + ips);
        }

        mailboxFactory.close();
    }

    public void test6() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories(mailboxFactory.createMailbox());
        factory.setParent(null);

        RootJid root = new RootJid(mailbox);
        root.setParent(factory);

        SetActor setMap = new SetActor(IntegerIntegerMapJidFactory.fac);
        setMap.send(future, root);
        IntegerIntegerMapJid map = (IntegerIntegerMapJid) GetActor.req.send(future, root);

        MapUAppender ba = new MapUAppender(mailbox);
        ba.setParent(factory);

        ba.count = 10;
        ba.repeat = 10;

        //ba.count = 10000;
        //ba.repeat = 1000;
        //Updates per second = 2486325

        map.initialCapacity = ba.count;

        ba.map = map;
        TimeMapUAppender.req.send(future, ba);
        long t = TimeMapUAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.count * ba.repeat / t;
            System.out.println("Updates per second = " + ips);
        }

        mailboxFactory.close();
    }
}
