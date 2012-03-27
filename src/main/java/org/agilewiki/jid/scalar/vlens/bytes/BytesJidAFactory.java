package org.agilewiki.jid.scalar.vlens.bytes;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a BytesJidA.
 */
public class BytesJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public BytesJidAFactory(String actorType) {
        super(actorType);
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
