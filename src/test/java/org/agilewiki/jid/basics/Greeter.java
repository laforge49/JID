package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class Greeter extends StringJid implements Main {
    public String greeting;

    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        if (greeting == null)
            throw new IllegalStateException("greeting is null");
        System.out.println(greeting + " " + getValue());
        rp.processResponse(null);
    }
}
