package org.agilewiki.jid.scalar.flens.integer;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a IntegerJidA.
 */
public class IntegerJidFactory extends ActorFactory {
    final public static IntegerJidFactory fac = new IntegerJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    public IntegerJidFactory() {
        super(JidFactories.INTEGER_JID_TYPE);
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
