package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Mailbox;
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
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected LongMapJid instantiateActor(Mailbox mailbox)
            throws Exception {
        LongMapJid imj = new LongMapJid(mailbox);
        imj.valueFactory = valueFactory;
        return imj;
    }
}
