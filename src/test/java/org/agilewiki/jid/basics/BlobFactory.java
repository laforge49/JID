package org.agilewiki.jid.basics;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;

public class BlobFactory extends ActorFactory {
    public BlobFactory(String actorType) {
        super(actorType);
    }

    @Override
    protected Blob instantiateActor()
            throws Exception {
        Blob blob = new Blob();
        blob.valueFactory = ActorJidFactory.fac;
        return blob;
    }
}
