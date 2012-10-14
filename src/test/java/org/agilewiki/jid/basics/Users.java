package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.vlenc.map.MapEntry;
import org.agilewiki.jid.collection.vlenc.map.StringMapJid;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

import java.util.Iterator;

public class Users extends StringMapJid implements Main {
    @Override
    public void processRequest(Proc request, RP rp) throws Exception {
        initializeList();
        Iterator<_Jid> it = list.iterator();
        while (it.hasNext()) {
            MapEntry<String, StringJid> tj = (MapEntry) it.next();
            String name = tj.getKey();
            StringJid email = (StringJid) tj.getValue();
            System.out.println("name: " + name + ", email: " + email.getValue());
        }
        rp.processResponse(null);
    }
}
