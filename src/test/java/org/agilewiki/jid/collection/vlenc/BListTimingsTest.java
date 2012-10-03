package org.agilewiki.jid.collection.vlenc;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;

public class BListTimingsTest extends TestCase {
    public void test() {

        int s = 100000;
        int r = 1000;
        // list size = 100000
        // repetitions = 1000
        // total run time (milliseconds) = 250
        // time per update (microseconds) = 250

        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Mailbox mailbox = mailboxFactory.createMailbox();
            JAFactory factory = new JAFactory();
            factory.initialize(mailbox);
            (new JidFactories()).initialize(mailbox, factory);
            BListJidFactory intListFactory = new BListJidFactory("il", IntegerJidFactory.fac);
            factory.registerActorFactory(intListFactory);
            BListJid intList1 = (BListJid) factory.newActor("il");
            int i = 0;
            while (i < 100000) {
                intList1.iAdd(-1);
                IntegerJid ij0 = (IntegerJid) intList1.iGet(-1);
                ij0.setValue(i);
                i += 1;
            }
            intList1.getSerializedBytes();
            long t0 = System.currentTimeMillis();
            int j = 0;
            while (j < r) {
                BListJid intList2 = (BListJid) intList1.copyJID(mailbox);
                intList1.iAdd(s / 2);
                intList2.getSerializedBytes();
                j += 1;
            }
            long t1 = System.currentTimeMillis();
            System.out.println("list size = " + s);
            System.out.println("repetitions = " + r);
            long rt = t1 - t0;
            System.out.println("total run time (milliseconds) = " + rt);
            long tpu = rt * 1000L / r;
            System.out.println("time per update (microseconds) = " + tpu);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
