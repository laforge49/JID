package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jid.collection.flenc.TupleJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class User extends TupleJid implements Main {
    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        StringJid name = (StringJid) iGet(0);
        System.out.println("name: " + name.getValue());
        IntegerJid age = (IntegerJid) iGet(1);
        System.out.println("age: " + age.getValue());
        StringJid location = (StringJid) iGet(2);
        System.out.println("location: " + location.getValue());
        rp.processResponse(null);
    }
}
