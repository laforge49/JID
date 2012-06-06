package org.agilewiki.jid.scalar.vlens.string;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactories;

/**
 * Creates a StringJid.
 */
public class StringJidFactory extends ActorFactory {
    final public static StringJidFactory fac = new StringJidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    protected StringJidFactory() {
        super(JidFactories.STRING_JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    final protected StringJid instantiateActor()
            throws Exception {
        return new StringJid();
    }
}
