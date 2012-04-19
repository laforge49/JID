package org.agilewiki.jid.scalar.vlens.string;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.JidFactory;

/**
 * Creates a StringJid.
 */
public class StringJidFactory extends JidFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public StringJidFactory() {
        actorType = JidFactories.STRING_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected StringJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new StringJid(mailbox);
    }
}
