package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.Factory;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.scalar.vlens.actor.UnionJid;

/**
 * Creates ListJids.
 */
public class ListJidFactory extends ActorFactory {
    private ActorFactory elementsFactory;
    private String elementsType;
    private int initialCapacity;

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param elementsFactory The elements factory.
     * @param initialCapacity The initial capacity.
     */
    public ListJidFactory(String actorType, ActorFactory elementsFactory, int initialCapacity) {
        super(actorType);
        this.elementsFactory = elementsFactory;
        this.initialCapacity = initialCapacity;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param elementsFactory The elements factory.
     */
    public ListJidFactory(String actorType, ActorFactory elementsFactory) {
        this(actorType, elementsFactory, 10);
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param elementsType    The elements type.
     * @param initialCapacity The initial capacity.
     */
    public ListJidFactory(String actorType, String elementsType, int initialCapacity) {
        super(actorType);
        this.elementsType = elementsType;
        this.initialCapacity = initialCapacity;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param elementsType The elements type.
     */
    public ListJidFactory(String actorType, String elementsType) {
        this(actorType, elementsType, 10);
    }

    /**
     * Initialize the new list.
     *
     * @param listJid The new list.
     */
    public void assignElementsFactory(ListJid listJid) {
        listJid.elementsFactory = elementsFactory;
        listJid.initialCapacity = initialCapacity;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected JLPCActor instantiateActor() throws Exception {
        ListJid listJid = new ListJid();
        assignElementsFactory(listJid);
        return listJid;
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    public JLPCActor newActor(Mailbox mailbox, Actor parent)
            throws Exception {
        ListJid lj = (ListJid) super.newActor(mailbox, parent);
        if (elementsFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            elementsFactory = f.getActorFactory(elementsType);
        }
        assignElementsFactory(lj);
        return lj;
    }
}
