package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.collection.vlenc.map.StringMapJid;
import org.agilewiki.jid.scalar.vlens.actor.ActorJid;

public class Blob extends StringMapJid implements Main {
    public Blob(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        initialize();
        ActorJid aj = (ActorJid) kGet("fun");
        Actor a = aj.getValue();
        Proc.req.send(this, a, rp);
    }
}
