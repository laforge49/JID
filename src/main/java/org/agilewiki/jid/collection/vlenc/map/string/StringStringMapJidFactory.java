package org.agilewiki.jid.collection.vlenc.map.string;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.jidFactory.JidAFactory;

/**
 * Creates a StringStringMapJid.
 */
public class StringStringMapJidFactory extends JidAFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public StringStringMapJidFactory() {
        actorType = JidFactories.STRING_STRING_MAP_JID_TYPE;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    final protected ListJid instantiateActor(Mailbox mailbox)
            throws Exception {
        return new StringStringMapJid(mailbox);
    }
}
