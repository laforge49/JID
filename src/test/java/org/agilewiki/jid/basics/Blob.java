package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.collection.vlenc.map.StringMapJid;
import org.agilewiki.jid.scalar.vlens.actor.ActorJid;

public class Blob extends StringMapJid implements Main {
    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        initializeList();
        ActorJid aj = (ActorJid) kGet("fun");
        Actor a = aj.getValue();
        Proc.req.send(this, a, rp);
    }
}
