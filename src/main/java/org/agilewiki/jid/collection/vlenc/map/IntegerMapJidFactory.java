package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Mailbox;
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
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected IntegerMapJid instantiateActor(Mailbox mailbox)
            throws Exception {
        IntegerMapJid imj = new IntegerMapJid(mailbox);
        imj.valueFactory = valueFactory;
        return imj;
    }
}
