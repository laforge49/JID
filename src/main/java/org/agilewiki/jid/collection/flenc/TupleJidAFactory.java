package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a TupleJidA.
 */
public class TupleJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public TupleJidAFactory() {
        actorType = JidFactories.TUPLE_JID_ATYPE;
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
