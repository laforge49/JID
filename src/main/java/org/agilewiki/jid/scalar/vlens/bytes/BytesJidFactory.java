package org.agilewiki.jid.scalar.vlens.bytes;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a BytesJidA.
 */
public class BytesJidFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public BytesJidFactory() {
        actorType = JidFactories.BYTES_JID_TYPE;
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
