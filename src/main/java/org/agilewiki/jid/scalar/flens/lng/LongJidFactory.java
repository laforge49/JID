package org.agilewiki.jid.scalar.flens.lng;

import org.agilewiki.jactor.Mailbox;
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
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected LongJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new LongJid(mailbox);
    }
}
