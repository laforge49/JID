package org.agilewiki.jid.scalar.vlens.bytes;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a BytesJidA.
 */
public class BytesJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public BytesJidAFactory() {
        actorType = JidFactories.BYTES_JID_ATYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected BytesJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new BytesJidA(mailbox);
    }
}
