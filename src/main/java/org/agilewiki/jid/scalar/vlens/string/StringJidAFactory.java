package org.agilewiki.jid.scalar.vlens.string;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a StringJidA.
 */
public class StringJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public StringJidAFactory() {
        actorType = JidFactories.STRING_JID_ATYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected StringJidA instantiateActor(Mailbox mailbox)
            throws Exception {
        return new StringJidA(mailbox);
    }
}
