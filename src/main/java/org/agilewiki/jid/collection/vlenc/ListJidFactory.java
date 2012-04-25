package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates ListJids.
 */
public class ListJidFactory extends ActorFactory {
    private ActorFactory elementsFactory;
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
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected JLPCActor instantiateActor(Mailbox mailbox) throws Exception {
        ListJid listJid = new ListJid(mailbox);
        assignElementsFactory(listJid);
        return listJid;
    }
}
