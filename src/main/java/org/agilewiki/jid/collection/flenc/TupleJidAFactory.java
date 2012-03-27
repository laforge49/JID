package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a TupleJidA.
 */
public class TupleJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public TupleJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected TupleJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new TupleJidA(mailbox);
    }
}
