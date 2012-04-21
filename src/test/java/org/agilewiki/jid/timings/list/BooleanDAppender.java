package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.vlenc.BooleanListJid;
import org.agilewiki.jid.collection.vlenc.BooleanListJidFactory;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.IAdd;

public class BooleanDAppender extends JLPCActor {
    public int count;
    public int repeat;
    public BooleanListJid list;
    private IAdd iAdd = new IAdd(-1);

    public BooleanDAppender(Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            iAdd.call(this, list);
            i += 1;
        }
        byte[] bytes = GetSerializedBytes.req.call(this, list);
        Empty.req.call(this, list);
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            ReadableBytes rb = new ReadableBytes(bytes, 0);
            BooleanListJid blj = (BooleanListJid) BooleanListJidFactory.fac.newActor(getMailbox(), getParent());
            blj.load(rb);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
