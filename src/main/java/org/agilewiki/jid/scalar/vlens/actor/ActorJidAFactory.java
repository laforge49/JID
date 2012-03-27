package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a ActorJidA.
 */
public class ActorJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public ActorJidAFactory() {
        actorType = JidFactories.ACTOR_JID_ATYPE;
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
