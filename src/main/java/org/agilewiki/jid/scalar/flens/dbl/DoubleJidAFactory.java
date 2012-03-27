package org.agilewiki.jid.scalar.flens.dbl;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a DoubleJidA.
 */
public class DoubleJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public DoubleJidAFactory() {
        actorType = JidFactories.DOUBLE_JID_ATYPE;
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
