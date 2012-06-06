package org.agilewiki.jid.scalar.flens.bool;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a BooleanJidA.
 */
public class BooleanJidFactory extends ActorFactory {
    final public static BooleanJidFactory fac = new BooleanJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected BooleanJidFactory() {
        super(JidFactories.BOOLEAN_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    final protected BooleanJid instantiateActor() throws Exception {
        return new BooleanJid();
    }
}
