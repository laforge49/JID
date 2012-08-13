package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJid;

public class MapAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerMapJid map;

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            int i = 0;
            while (i < count) {
                map.newKMake(i).call(this, map);
                i += 1;
            }
            map.empty();
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
