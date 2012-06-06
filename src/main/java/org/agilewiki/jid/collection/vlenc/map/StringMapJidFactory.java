package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates StringMapJid's.
 */
public class StringMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     */
    public StringMapJidFactory(String actorType, ActorFactory valueFactory) {
        super(actorType);
        this.valueFactory = valueFactory;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected StringMapJid instantiateActor()
            throws Exception {
        StringMapJid imj = new StringMapJid();
        imj.valueFactory = valueFactory;
        return imj;
    }
}
