package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a ListJidA.
 */
public class ListJidAFactory extends JidAFactory {
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
    final protected ListJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new ListJidA(mailbox);
    }
}
