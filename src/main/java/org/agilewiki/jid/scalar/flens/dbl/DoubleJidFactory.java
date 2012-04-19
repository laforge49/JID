package org.agilewiki.jid.scalar.flens.dbl;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.JidFactory;

/**
 * Creates a DoubleJidA.
 */
public class DoubleJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public DoubleJidFactory() {
        actorType = JidFactories.DOUBLE_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected DoubleJid instantiateActor(Mailbox mailbox) throws Exception {
        return new DoubleJid(mailbox);
    }
}
