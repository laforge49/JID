package org.agilewiki.jid.scalar.flens.lng;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a LongJidA.
 */
public class LongJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public LongJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected LongJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new LongJidA(mailbox);
    }
}
