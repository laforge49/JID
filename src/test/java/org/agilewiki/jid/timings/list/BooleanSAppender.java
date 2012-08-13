package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.IAdd;
import org.agilewiki.jid.collection.vlenc.ListJid;

public class BooleanSAppender extends JLPCActor {
    public int count;
    public int repeat;
    public ListJid list;
    private IAdd iAdd = new IAdd(-1);

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            int i = 0;
            while (i < count) {
                iAdd.call(this, list);
                i += 1;
            }
            GetSerializedBytes.req.call(this, list);
            list.empty();
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
