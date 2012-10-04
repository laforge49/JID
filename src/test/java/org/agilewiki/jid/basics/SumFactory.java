package org.agilewiki.jid.basics;

import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;

public class SumFactory extends ListJidFactory {
    public SumFactory(String actorType) {
        super(actorType, IntegerJidFactory.fac);
    }

    @Override
    protected Sum instantiateActor()
            throws Exception {
        Sum sum = new Sum();
        return sum;
    }
}
