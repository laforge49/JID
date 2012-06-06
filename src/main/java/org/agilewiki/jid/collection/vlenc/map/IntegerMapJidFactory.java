package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates IntegerMapJid's.
 */
public class IntegerMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     */
    public IntegerMapJidFactory(String actorType, ActorFactory valueFactory) {
        super(actorType);
        this.valueFactory = valueFactory;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected IntegerMapJid instantiateActor()
            throws Exception {
        IntegerMapJid imj = new IntegerMapJid();
        imj.valueFactory = valueFactory;
        return imj;
    }
}
