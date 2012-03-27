package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidAFactory;

/**
 * Creates a ActorJidA.
 */
public class ActorJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public ActorJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected ActorJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new ActorJidA(mailbox);
    }
}
