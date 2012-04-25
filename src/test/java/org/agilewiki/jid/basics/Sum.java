package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;

import java.util.Iterator;

public class Sum extends ListJid implements Main {
    public Sum(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request.getClass() == Proc.class) {
            initialize();
            int sum = 0;
            Iterator<_Jid> it = list.iterator();
            while (it.hasNext()) {
                IntegerJid ij = (IntegerJid) it.next();
                sum += ij.getValue();
            }
            System.out.println("Total: " + sum);
            rp.processResponse(null);
            return;
        }

        super.processRequest(request, rp);
    }
}
