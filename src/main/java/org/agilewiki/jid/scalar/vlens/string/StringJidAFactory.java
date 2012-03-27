package org.agilewiki.jid.scalar.vlens.string;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a StringJidA.
 */
public class StringJidAFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public StringJidAFactory(String actorType) {
        super(actorType);
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
