package org.agilewiki.jid.jidFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid._Jid;

/**
 * Creates a Jid.
 */
public class JidFactory extends ActorFactory {
    /**
     * Create a JLPCActorFactory.
     */
    public JidFactory() {
        super(JidFactories.JID_TYPE);
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox   The mailbox of the new actor.
     * @param parent    The parent of the new actor.
     * @param container The container of the new Jid.
     * @param bytes     Holds the serialized data.
     * @return The new actor.
     */
    public Jid newJID(Mailbox mailbox, Actor parent, _Jid container, byte[] bytes)
            throws Exception {
        return newJID(mailbox, parent, container, new ReadableBytes(bytes, 0));
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox   The mailbox of the new actor.
     * @param parent    The parent of the new actor.
     * @param container The container of the new Jid.
     * @return The new actor.
     */
    final public Jid newJID(Mailbox mailbox, Actor parent, _Jid container)
            throws Exception {
        return newJID(mailbox, parent, container, (ReadableBytes) null);
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

    /**
     * Create and configure an actor.
     *
     * @param mailbox       The mailbox of the new actor.
     * @param parent        The parent of the new actor.
     * @param readableBytes Holds the serialized data.
     * @param container     The container of the new Jid.
     * @return The new actor.
     */
    final public Jid newJID(Mailbox mailbox, Actor parent, _Jid container, ReadableBytes readableBytes)
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

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    final public Actor newActor(Mailbox mailbox, Actor parent, _Jid container, ReadableBytes readableBytes)
            throws Exception {
        return newJID(mailbox, parent, container, readableBytes).thisActor();
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    @Override
    final public Jid newActor(Mailbox mailbox, Actor parent) throws Exception {
        return newJID(mailbox, parent, null).thisActor();
    }
}
