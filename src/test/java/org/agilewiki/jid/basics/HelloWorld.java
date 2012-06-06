package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jid.Jid;

public class HelloWorld extends Jid implements Main {
    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        System.out.println("Hello world!");
        rp.processResponse(null);
    }
}
