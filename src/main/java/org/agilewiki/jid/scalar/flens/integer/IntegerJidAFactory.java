package org.agilewiki.jid.scalar.flens.integer;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidAFactory;

/**
 * Creates a IntegerJidA.
 */
public class IntegerJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public IntegerJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected IntegerJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new IntegerJidA(mailbox);
    }
}
