package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.Factory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates LongMapJid's.
 */
public class LongMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;
    private String valueType;

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
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     */
    public LongMapJidFactory(String actorType, String valueType) {
        super(actorType);
        this.valueType = valueType;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected LongMapJid instantiateActor()
            throws Exception {
        return new LongMapJid();
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    public JLPCActor newActor(Mailbox mailbox, Actor parent)
            throws Exception {
        LongMapJid imj = (LongMapJid) super.newActor(mailbox, parent);
        if (valueFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            valueFactory = f.getActorFactory(valueType);
        }
        imj.valueFactory = valueFactory;
        return imj;
    }
}
