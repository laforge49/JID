package org.agilewiki.jid.jidFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.components.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid._Jid;

/**
 * Creates a JidA.
 */
public class JidFactory extends _JidFactory implements ActorFactory {
    /**
     * The actor type.
     */
    protected String actorType;

    /**
     * Create a JLPCActorFactory.
     */
    public JidFactory() {
        actorType = JidFactories.JID_TYPE;
    }

    /**
     * Returns the actor type.
     *
     * @return The actor type.
     */
    @Override
    final public String getActorType() {
        return actorType;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    protected Jid instantiateActor(Mailbox mailbox) throws Exception {
        return new Jid(mailbox);
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox       The mailbox of the new actor.
     * @param parent        The parent of the new actor.
     * @param readableBytes Holds the serialized data.
     * @param container     The container of the new Jid.
     * @return The new actor.
     */
    final public _Jid newJID(Mailbox mailbox, Actor parent, _Jid container, ReadableBytes readableBytes)
            throws Exception {
        Jid jidA = instantiateActor(mailbox);
        jidA.setActorType(actorType);
        jidA.setParent(parent);
        if (readableBytes != null)
            jidA.load(readableBytes);
        if (container != null)
            jidA.setContainerJid(container);
        return jidA;
    }
}
