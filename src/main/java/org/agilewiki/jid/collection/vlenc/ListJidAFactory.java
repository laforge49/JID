package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.components.factory.JLPCActorFactory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates a ListJidA.
 */
public class ListJidAFactory extends JLPCActorFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public ListJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected JLPCActor instantiateActor(Mailbox mailbox)
            throws Exception {
        return new ListJidA(mailbox);
    }
}
