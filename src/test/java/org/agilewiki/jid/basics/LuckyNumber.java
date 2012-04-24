package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;

public class LuckyNumber extends IntegerJid implements Main {
    public LuckyNumber(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request.getClass() == Proc.class) {
            System.out.println("Your lucky number is " + getValue());
            rp.processResponse(null);
            return;
        }

        super.processRequest(request, rp);
    }
}
