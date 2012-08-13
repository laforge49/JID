package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.IAdd;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.scalar.flens.bool.BooleanJid;
import org.agilewiki.jid.scalar.flens.bool.BooleanJidFactory;
import org.agilewiki.jid.scalar.flens.bool.SetBoolean;

public class BooleanUAppender extends JLPCActor {
    public int count;
    public int repeat;
    public ListJid list;
    private IAdd iAdd = new IAdd(-1);

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            iAdd.call(this, list);
            i += 1;
        }
        byte[] bytes = GetSerializedBytes.req.call(this, list);
        list.empty();
        SetBoolean sbTrue = new SetBoolean(true);
        long t0 = System.currentTimeMillis();
        int j = 0;

        while (j < repeat) {
            ReadableBytes rb = new ReadableBytes(bytes, 0);
            ListJid blj = (ListJid) (new ListJidFactory(JidFactories.BOOLEAN_LIST_JID_TYPE, BooleanJidFactory.fac)).
                    newActor(getMailbox(), getParent());
            blj.load(rb);
            IGet ig = new IGet(j);
            BooleanJid bj = (BooleanJid) ig.call(this, blj);
            sbTrue.call(this, bj);
            bytes = GetSerializedBytes.req.call(this, blj);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
