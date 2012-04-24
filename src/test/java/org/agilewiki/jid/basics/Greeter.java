package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class Greeter extends StringJid implements Main {
    public String greeting;

    public Greeter(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request.getClass() == Proc.class) {
            if (greeting == null)
                throw new IllegalStateException("greeting is null");
            System.out.println(greeting + " " + getValue());
            rp.processResponse(null);
            return;
        }

        super.processRequest(request, rp);
    }
}
