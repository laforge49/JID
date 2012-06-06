package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;

public class LuckyNumber extends IntegerJid implements Main {
    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        System.out.println("Your lucky number is " + getValue());
        rp.processResponse(null);
    }
}
