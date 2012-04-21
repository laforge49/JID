package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.map.integer.IntegerIntegerMapJid;
import org.agilewiki.jid.collection.vlenc.map.integer.IntegerIntegerMapJidFactory;

public class MapDAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerIntegerMapJid map;

    public MapDAppender(Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            map.newKMake(i).call(this, map);
            i += 1;
        }
        byte[] bytes = GetSerializedBytes.req.call(this, map);
        Empty.req.call(this, map);
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            ReadableBytes rb = new ReadableBytes(bytes, 0);
            IntegerIntegerMapJid blj = (IntegerIntegerMapJid)
                    IntegerIntegerMapJidFactory.fac.newActor(getMailbox(), getParent());
            blj.load(rb);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
