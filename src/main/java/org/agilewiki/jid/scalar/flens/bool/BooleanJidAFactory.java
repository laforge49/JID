package org.agilewiki.jid.scalar.flens.bool;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a BooleanJidA.
 */
public class BooleanJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public BooleanJidAFactory() {
        actorType = JidFactories.BOOLEAN_JID_ATYPE;
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
