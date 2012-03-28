package org.agilewiki.jid.scalar.flens.integer;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a IntegerJidA.
 */
public class IntegerJidFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public IntegerJidFactory() {
        actorType = JidFactories.INTEGER_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected IntegerJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new IntegerJid(mailbox);
    }
}
