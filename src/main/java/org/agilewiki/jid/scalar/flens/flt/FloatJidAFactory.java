package org.agilewiki.jid.scalar.flens.flt;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a FloatJidA.
 */
public class FloatJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public FloatJidAFactory(String actorType) {
        super(actorType);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected FloatJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new FloatJidA(mailbox);
    }
}
