package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJid;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.flens.integer.SetInteger;

public class MapUAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerMapJid map;
    private final SetInteger ss = new SetInteger(42);

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            map.kMake(i);
            i += 1;
        }
        byte[] bytes = map.getSerializedBytes();
        map.empty();
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            ReadableBytes rb = new ReadableBytes(bytes, 0);
            IntegerMapJid blj = (IntegerMapJid)
                    (new IntegerMapJidFactory(JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, IntegerJidFactory.fac)).
                            newActor(getMailbox(), getParent());
            blj.load(rb);
            IntegerJid sj = (IntegerJid) blj.kGet(j);
            ss.call(this, sj);
            bytes = blj.getSerializedBytes();
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
