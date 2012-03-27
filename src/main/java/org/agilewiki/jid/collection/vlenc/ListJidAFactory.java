package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a ListJidA.
 */
public class ListJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public ListJidAFactory() {
        actorType = JidFactories.LIST_JID_ATYPE;
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
