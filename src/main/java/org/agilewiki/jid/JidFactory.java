package org.agilewiki.jid;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates a Jid.
 */
public class JidFactory extends ActorFactory {
    final public static JidFactory fac = new JidFactory();

    /**
     * Create a JLPCActorFactory.
     */
    public JidFactory() {
        super(JidFactories.JID_TYPE);
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected Jid instantiateActor(Mailbox mailbox) throws Exception {
        return new Jid(mailbox);
    }
}
