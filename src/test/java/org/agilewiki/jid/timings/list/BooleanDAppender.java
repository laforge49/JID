package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.IAdd;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class BooleanDAppender extends JLPCActor {
    public int count;
    public int repeat;
    public ListJid list;
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
            ListJid blj = (ListJid) (new ListJidFactory(JidFactories.STRING_LIST_JID_TYPE, StringJidFactory.fac)).newActor(getMailbox(), getParent());
            blj.load(rb);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
