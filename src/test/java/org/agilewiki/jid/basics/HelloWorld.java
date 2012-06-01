package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.Jid;

public class HelloWorld extends Jid implements Main {
    public HelloWorld(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        System.out.println("Hello world!");
        rp.processResponse(null);
    }
}
