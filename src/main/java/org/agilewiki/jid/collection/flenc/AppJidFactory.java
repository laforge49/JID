package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.Factory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates AppJid objects.
 */
abstract public class AppJidFactory extends ActorFactory {
    private ActorFactory[] tupleFactories;
    private String[] actorTypes;

    public AppJidFactory(String subActorType) {
        super(subActorType);
        this.tupleFactories = new ActorFactory[0];
    }

    /**
     * Create a JLPCActorFactory.
     *
     * @param subActorType   The actor type.
     * @param tupleFactories The element factories.
     */
    public AppJidFactory(String subActorType, ActorFactory... tupleFactories) {
        super(subActorType);
        this.tupleFactories = tupleFactories;
    }

    /**
     * Create a JLPCActorFactory.
     *
     * @param subActorType The actor type.
     * @param actorTypes   The element types.
     */
    public AppJidFactory(String subActorType, String... actorTypes) {
        super(subActorType);
        this.actorTypes = actorTypes;
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
        AppJid tj = (AppJid) super.newActor(mailbox, parent);
        if (tupleFactories == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            ActorFactory[] afs = new ActorFactory[actorTypes.length];
            int i = 0;
            while (i < actorTypes.length) {
                afs[i] = f.getActorFactory(actorTypes[i]);
                i += 1;
            }
            tupleFactories = afs;
        }
        tj.tupleFactories = tupleFactories;
        return tj;
    }
}
