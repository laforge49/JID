/*
 * Copyright 2012 Bill La Forge
 *
 * This file is part of AgileWiki and is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (LGPL) as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * or navigate to the following url http://www.gnu.org/licenses/lgpl-2.1.txt
 *
 * Note however that only Scala, Java and JavaScript files are being covered by LGPL.
 * All other files are covered by the Common Public License (CPL).
 * A copy of this license is also included and can be
 * found as well at http://www.opensource.org/licenses/cpl1.0.txt
 */
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
    private ActorFactory entryFactory;
    private String entryType;
    private int nodeCapacity = 28;
    private boolean isRoot = true;
    private boolean auto = true;

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param entryFactory The entry factory.
     */
    public BListJidFactory(String actorType, ActorFactory entryFactory) {
        super(actorType);
        this.entryFactory = entryFactory;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param entryFactory The entry factory.
     * @param nodeCapacity The size of the nodes.
     */
    public BListJidFactory(String actorType, ActorFactory entryFactory,
                           int nodeCapacity) {
        super(actorType);
        this.entryFactory = entryFactory;
        this.nodeCapacity = nodeCapacity;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param entryFactory The entry factory.
     * @param nodeCapacity The size of the nodes.
     * @param isRoot       Create a root node when true.
     * @param auto         Define the node as a leaf when true.
     */
    public BListJidFactory(String actorType, ActorFactory entryFactory,
                           int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.entryFactory = entryFactory;
        this.nodeCapacity = nodeCapacity;
        this.isRoot = isRoot;
        this.auto = auto;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     * @param entryType The entry type.
     */
    public BListJidFactory(String actorType, String entryType) {
        super(actorType);
        this.entryType = entryType;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param entryType    The entry type.
     * @param nodeCapacity The size of the nodes.
     * @param isRoot       Create a root node when true.
     * @param auto         Define the node as a leaf when true.
     */
    public BListJidFactory(String actorType, String entryType,
                           int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.entryType = entryType;
        this.nodeCapacity = nodeCapacity;
        this.isRoot = isRoot;
        this.auto = auto;
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
        if (entryFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            entryFactory = f.getActorFactory(entryType);
        }
        lj.entryFactory = entryFactory;
        lj.nodeCapacity = nodeCapacity;
        lj.isRoot = isRoot;
        lj.init();
        if (auto)
            lj.setNodeType("leaf");
        return lj;
    }
}
