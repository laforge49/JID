package org.agilewiki.jid.scalar.flens.bool;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidAFactory;

/**
 * Creates a BooleanJidA.
 */
public class BooleanJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public BooleanJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected BooleanJidA instantiateActor(Mailbox mailbox) throws Exception {
        return new BooleanJidA(mailbox);
    }
}
