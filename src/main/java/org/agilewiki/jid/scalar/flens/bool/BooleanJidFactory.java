package org.agilewiki.jid.scalar.flens.bool;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.JidFactory;

/**
 * Creates a BooleanJidA.
 */
public class BooleanJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public BooleanJidFactory() {
        actorType = JidFactories.BOOLEAN_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected BooleanJid instantiateActor(Mailbox mailbox) throws Exception {
        return new BooleanJid(mailbox);
    }
}
