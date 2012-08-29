package org.agilewiki.jid.timings.list;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJid;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.GetActor;
import org.agilewiki.jid.scalar.vlens.actor.RootJid;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class BigIntegerIntegerMapTest extends TestCase {
    public void test1() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFuture future = new JAFuture();

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(new IntegerMapJidFactory(JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

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

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(
                new IntegerMapJidFactory(JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

        MapAppender ba = new MapAppender();
        ba.initialize(mailbox, factory);

        ba.count = 10;
        ba.repeat = 10;

        //System.out.println("###########################################################");
        //ba.count = 5000;
        //ba.repeat = 1000;
        //Appends per second = 5,827,505

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

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(new IntegerMapJidFactory(
                JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

        MapAppender ba = new MapAppender();
        ba.initialize(mailbox, factory);

        ba.count = 10;
        ba.repeat = 10;

        //System.out.println("###########################################################");
        //ba.count = 10000;
        //ba.repeat = 1000;
        //Appends per second = 4,533,091

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

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(new IntegerMapJidFactory(
                JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

        MapSAppender ba = new MapSAppender();
        ba.initialize(mailbox, factory);

        ba.count = 10;
        ba.repeat = 10;

        //System.out.println("###########################################################");
        //ba.count = 10000;
        //ba.repeat = 1000;
        //Appends and serializes per second = 5,070,993

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

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(new IntegerMapJidFactory(
                JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

        MapDAppender ba = new MapDAppender();
        ba.initialize(mailbox, factory);

        ba.count = 10;
        ba.repeat = 10;

        //System.out.println("###########################################################");
        //ba.count = 10000;
        //ba.repeat = 10000;
        //Deserializes per second = 33,333,333,333

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

        JidFactories factory = new JidFactories();
        factory.initialize(mailboxFactory.createMailbox());

        RootJid root = new RootJid();
        root.initialize(mailbox, factory);

        SetActor setMap = new SetActor(new IntegerMapJidFactory(
                JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac));
        setMap.send(future, root);
        IntegerMapJid map = (IntegerMapJid) GetActor.req.send(future, root);

        MapUAppender ba = new MapUAppender();
        ba.initialize(mailbox, factory);

        ba.count = 10;
        ba.repeat = 10;

        //System.out.println("###########################################################");
        //ba.count = 10000;
        //ba.repeat = 1000;
        //Updates per second = 1152

        map.initialCapacity = ba.count;

        ba.map = map;
        TimeMapUAppender.req.send(future, ba);
        long t = TimeMapUAppender.req.send(future, ba);
        System.out.println("map size = " + ba.count);
        System.out.println("repeats = " + ba.repeat);
        if (t > 0) {
            long ips = 1000L * ba.repeat / t;
            System.out.println("Updates per second = " + ips);
        }

        mailboxFactory.close();
    }
}
