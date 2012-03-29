package org.agilewiki.jid.scalar.flens.flt;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidFactory;

/**
 * Creates a FloatJidA.
 */
public class FloatJidAFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public FloatJidAFactory() {
        actorType = JidFactories.FLOAT_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected FloatJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new FloatJid(mailbox);
    }
}
