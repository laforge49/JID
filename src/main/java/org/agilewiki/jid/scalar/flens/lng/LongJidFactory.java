package org.agilewiki.jid.scalar.flens.lng;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidFactory;

/**
 * Creates a LongJidA.
 */
public class LongJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public LongJidFactory() {
        actorType = JidFactories.LONG_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected LongJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new LongJid(mailbox);
    }
}
