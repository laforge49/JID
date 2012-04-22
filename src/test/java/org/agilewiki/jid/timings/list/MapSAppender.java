package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.map.IntegerMapJid;

public class MapSAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerMapJid map;

    public MapSAppender(Mailbox mailbox) {
        super(mailbox);
    }

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
            GetSerializedBytes.req.call(this, map);
            Empty.req.call(this, map);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
