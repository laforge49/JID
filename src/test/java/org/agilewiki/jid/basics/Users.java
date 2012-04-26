package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.flenc.TupleJid;
import org.agilewiki.jid.collection.vlenc.map.StringMapJid;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

import java.util.Iterator;

public class Users extends StringMapJid implements Main {
    public Users(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request.getClass() == Proc.class) {
            initialize();
            Iterator<_Jid> it = list.iterator();
            while (it.hasNext()) {
                TupleJid tj = (TupleJid) it.next();
                StringJid name = (StringJid) tj.iGet(0);
                StringJid email = (StringJid) tj.iGet(1);
                System.out.println("name: " + name.getValue() + ", email: " + email.getValue());
            }
            rp.processResponse(null);
            return;
        }

        super.processRequest(request, rp);
    }
}
