package org.agilewiki.jid.scalar.flens.dbl;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidAFactory;

/**
 * Creates a DoubleJidA.
 */
public class DoubleJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public DoubleJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected DoubleJidA instantiateActor(Mailbox mailbox) throws Exception {
        return new DoubleJidA(mailbox);
    }
}
