package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a ActorJid.
 */
public class ActorJidFactory extends ActorFactory {
    final public static ActorJidFactory fac = new ActorJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected ActorJidFactory() {
        super(JidFactories.ACTOR_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    final protected ActorJid instantiateActor()
            throws Exception {
        return new ActorJid();
    }
}
