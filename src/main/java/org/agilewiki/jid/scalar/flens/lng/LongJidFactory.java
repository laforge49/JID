package org.agilewiki.jid.scalar.flens.lng;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a LongJidA.
 */
public class LongJidFactory extends ActorFactory {
    final public static LongJidFactory fac = new LongJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected LongJidFactory() {
        super(JidFactories.LONG_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    final protected LongJid instantiateActor()
            throws Exception {
        return new LongJid();
    }
}
