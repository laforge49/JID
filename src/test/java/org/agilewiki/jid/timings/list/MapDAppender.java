package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.Empty;
import org.agilewiki.jid.collection.vlenc.map.integer.IntegerIntegerMapJid;
import org.agilewiki.jid.jidFactory.NewJID;

public class MapDAppender extends JLPCActor {
    public int count;
    public int repeat;
    public IntegerIntegerMapJid map;

    public MapDAppender(Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object o, RP rp) throws Exception {
        int i = 0;
        while (i < count) {
            map.newKMake(i).call(this, map);
            i += 1;
        }
        byte[] bytes = GetSerializedBytes.req.call(this, map);
        Empty.req.call(this, map);
        NewJID newMap = new NewJID(JidFactories.INTEGER_INTEGER_MAP_JID_TYPE, getMailbox(), bytes);
        long t0 = System.currentTimeMillis();
        int j = 0;
        while (j < repeat) {
            IntegerIntegerMapJid blj = (IntegerIntegerMapJid) newMap.call(this);
            j += 1;
        }
        long t1 = System.currentTimeMillis();
        rp.processResponse(t1 - t0);
    }
}
