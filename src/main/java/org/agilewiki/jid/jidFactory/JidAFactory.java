package org.agilewiki.jid.jidFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.components.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.JidA;
import org.agilewiki.jid.ReadableBytes;

/**
 * Creates a JidA.
 */
public class JidAFactory extends JidFactory implements ActorFactory {
    /**
     * The actor type.
     */
    protected String actorType;

    /**
     * Create a JLPCActorFactory.
     *
     * @param actorType The name of the actor type.
     */
    public JidAFactory(String actorType) {
        this.actorType = actorType;
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
    protected JidA instantiateActor(Mailbox mailbox) throws Exception {
        return new JidA(mailbox);
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
    final public Jid newJID(Mailbox mailbox, Actor parent, Jid container, ReadableBytes readableBytes)
            throws Exception {
        JidA jidA = instantiateActor(mailbox);
        jidA.setActorType(actorType);
        jidA.setParent(parent);
        if (readableBytes != null)
            jidA.load(readableBytes);
        if (container != null)
            jidA.setContainerJid(container);
        return jidA;
    }
}
