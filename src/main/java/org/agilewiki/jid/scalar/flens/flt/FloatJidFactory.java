package org.agilewiki.jid.scalar.flens.flt;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a FloatJidA.
 */
public class FloatJidFactory extends ActorFactory {
    final public static FloatJidFactory fac = new FloatJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected FloatJidFactory() {
        super(JidFactories.FLOAT_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected FloatJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new FloatJid(mailbox);
    }
}
