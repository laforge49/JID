package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a RootJid.
 */
public class RootJidFactory extends ActorFactory {
    final public static RootJidFactory fac = new RootJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected RootJidFactory() {
        super(JidFactories.ROOT_JID_TYPE);
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
