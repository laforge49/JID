package org.agilewiki.jid.scalar.flens.lng;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a LongJidA.
 */
public class LongJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public LongJidAFactory() {
        actorType = JidFactories.LONG_JID_ATYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected LongJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new LongJidA(mailbox);
    }
}
