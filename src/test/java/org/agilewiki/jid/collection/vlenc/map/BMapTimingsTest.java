package org.agilewiki.jid.collection.vlenc.map;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;

public class BMapTimingsTest extends TestCase {
    public void test1() throws Exception {

        int s = 1000;
        int r = 1000;

        //map size = 10000
        //repetitions = 10000
        //total run time (milliseconds) = 567
        //time per update (microseconds) = 56

        //map size = 100000
        //repetitions = 10000
        //total run time (milliseconds) = 4775
        //time per update (microseconds) = 477

        //map size = 1000000
        //repetitions = 1000
        //total run time (milliseconds) = 6854
        //time per update (microseconds) = 6854

        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BMapJid<Integer, IntegerJid> m1 = (BMapJid) factory.newActor(JidFactories.INTEGER_INTEGER_BMAP_JID_TYPE);
            int i = 0;
            while (i < s) {
                m1.kMake(i);
                IntegerJid ij0 = m1.kGet(i);
                ij0.setValue(i);
                i += 1;
            }
            m1.getSerializedBytes();
            long t0 = System.currentTimeMillis();
            int j = 0;
            i = s / 2;
            while (j < r) {
                BMapJid m2 = (BMapJid) m1.copyJID(mailbox);
                IntegerJid ij0 = m1.kGet(i);
                ij0.setValue(-i);
                m2.getSerializedBytes();
                j += 1;
            }
            long t1 = System.currentTimeMillis();
            System.out.println("map size = " + s);
            System.out.println("repetitions = " + r);
            long rt = t1 - t0;
            System.out.println("total run time (milliseconds) = " + rt);
            long tpu = rt * 1000L / r;
            System.out.println("time per update (microseconds) = " + tpu);
        } finally {
            mailboxFactory.close();
        }
    }
}
