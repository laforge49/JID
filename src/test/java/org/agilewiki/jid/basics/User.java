package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.collection.flenc.TupleJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class User extends TupleJid implements Main {
    public User(final Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request.getClass() == Proc.class) {
            StringJid name = (StringJid) iGet(0);
            System.out.println("name: " + name.getValue());
            IntegerJid age = (IntegerJid) iGet(1);
            System.out.println("age: " + age.getValue());
            StringJid location = (StringJid) iGet(2);
            System.out.println("location: " + name.getValue());
            rp.processResponse(null);
            return;
        }

        super.processRequest(request, rp);
    }
}
