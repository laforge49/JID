package org.agilewiki.jid.scalar.flens.integer;

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
    protected IntegerJidFactory() {
        super(JidFactories.INTEGER_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    final protected IntegerJid instantiateActor()
            throws Exception {
        return new IntegerJid();
    }
}
