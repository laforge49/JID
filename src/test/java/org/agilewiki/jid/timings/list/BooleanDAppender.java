package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class BooleanDAppender extends JLPCActor {
    public int count;
    public int repeat;
    public ListJid list;

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            list.iAdd(-1);
            i += 1;
        }
        byte[] bytes = list.getSerializedBytes();
        list.empty();
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            ReadableBytes rb = new ReadableBytes(bytes, 0);
            ListJid blj = (ListJid) (new ListJidFactory(JidFactories.STRING_LIST_JID_TYPE, StringJidFactory.fac)).newActor(getMailbox(), getParent());
            blj.load(rb);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
