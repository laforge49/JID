package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.Factory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates ListJids.
 */
public class BListJidFactory extends ActorFactory {
    private ActorFactory elementsFactory;
    private String elementsType;

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param elementsFactory The elements factory.
     */
    public BListJidFactory(String actorType, ActorFactory elementsFactory) {
        super(actorType);
        this.elementsFactory = elementsFactory;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param elementsType    The elements type.
     */
    public BListJidFactory(String actorType, String elementsType) {
        super(actorType);
        this.elementsType = elementsType;
    }

    /**
     * Initialize the new list.
     *
     * @param listJid The new list.
     */
    public void assignElementsFactory(BListJid listJid) {
        listJid.elementsFactory = elementsFactory;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected JLPCActor instantiateActor() throws Exception {
        return new BListJid();
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
        BListJid lj = (BListJid) super.newActor(mailbox, parent);
        if (elementsFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            elementsFactory = f.getActorFactory(elementsType);
        }
        assignElementsFactory(lj);
        lj.isRoot = true;
        lj.setLeafNode(true);
        return lj;
    }
}
