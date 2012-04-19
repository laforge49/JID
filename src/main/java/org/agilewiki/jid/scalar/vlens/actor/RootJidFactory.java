package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.JidFactory;

/**
 * Creates a RootJid.
 */
public class RootJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public RootJidFactory() {
        actorType = JidFactories.ROOT_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected RootJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new RootJid(mailbox);
    }
}
