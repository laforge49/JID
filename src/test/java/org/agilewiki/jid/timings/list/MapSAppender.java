package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJid;

public class MapSAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerMapJid map;

    protected long time() throws Exception {
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            int i = 0;
            while (i < count) {
                map.kMake(i);
                i += 1;
            }
            map.getSerializedBytes();
            map.empty();
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }
}
