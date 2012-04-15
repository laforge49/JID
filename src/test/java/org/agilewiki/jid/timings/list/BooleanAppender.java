package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.collection.vlenc.BooleanListJid;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.IAdd;

public class BooleanAppender extends JLPCActor {
    public int count;
    public int repeat;
    public BooleanListJid list;
    private IAdd iAdd = new IAdd(-1);

    public BooleanAppender(Mailbox mailbox) {
        super(mailbox);
    }

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
            Empty.req.call(this, list);
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
