package org.agilewiki.jid.scalar.vlens.bytes;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a BytesJidA.
 */
public class BytesJidFactory extends ActorFactory {
    final public static BytesJidFactory fac = new BytesJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected BytesJidFactory() {
        super(JidFactories.BYTES_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected BytesJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new BytesJid(mailbox);
    }
}
