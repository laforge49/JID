package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates LongMapJid's.
 */
public class LongMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     */
    public LongMapJidFactory(String actorType, ActorFactory valueFactory) {
        super(actorType);
        this.valueFactory = valueFactory;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected LongMapJid instantiateActor()
            throws Exception {
        LongMapJid imj = new LongMapJid();
        imj.valueFactory = valueFactory;
        return imj;
    }
}
