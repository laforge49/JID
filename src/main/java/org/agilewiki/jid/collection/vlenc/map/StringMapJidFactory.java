package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Mailbox;
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
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected StringMapJid instantiateActor(Mailbox mailbox)
            throws Exception {
        StringMapJid imj = new StringMapJid(mailbox);
        imj.valueFactory = valueFactory;
        return imj;
    }
}
