package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidFactory;

/**
 * Creates a ActorJid.
 */
public class ActorJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public ActorJidFactory() {
        actorType = JidFactories.ACTOR_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected ActorJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new ActorJid(mailbox);
    }
}
