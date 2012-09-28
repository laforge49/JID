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
    private int nodeCapacity = 28;
    private boolean isRoot = true;
    private boolean auto = true;

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
     * @param elementsFactory The elements factory.
     * @param nodeCapacity    The size of the nodes.
     */
    public BListJidFactory(String actorType, ActorFactory elementsFactory,
                           int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.elementsFactory = elementsFactory;
        this.nodeCapacity = nodeCapacity;
        this.isRoot = isRoot;
        this.auto = auto;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param elementsType The elements type.
     */
    public BListJidFactory(String actorType, String elementsType) {
        super(actorType);
        this.elementsType = elementsType;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param elementsType The elements type.
     * @param nodeCapacity The size of the nodes.
     */
    public BListJidFactory(String actorType, String elementsType,
                           int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.elementsType = elementsType;
        this.nodeCapacity = nodeCapacity;
        this.isRoot = isRoot;
        this.auto = auto;
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
        lj.nodeCapacity = nodeCapacity;
        lj.isRoot = isRoot;
        lj.init();
        if (auto)
            lj.setNodeType("leaf");
        return lj;
    }
}
