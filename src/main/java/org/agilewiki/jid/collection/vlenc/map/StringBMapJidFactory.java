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
package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.Factory;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Creates StringBMapJid's.
 */
public class StringBMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;
    private String valueType;
    private int nodeCapacity = 28;
    private boolean isRoot = true;
    private boolean auto = true;

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param valueFactory The value factory.
     */
    public StringBMapJidFactory(String actorType, ActorFactory valueFactory) {
        super(actorType);
        this.valueFactory = valueFactory;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param valueFactory The value factory.
     * @param nodeCapacity The size of the nodes.
     * @param isRoot       Create a root node when true.
     * @param auto         Define the node as a leaf when true.
     */
    public StringBMapJidFactory(String actorType, ActorFactory valueFactory,
                                int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.valueFactory = valueFactory;
        this.nodeCapacity = nodeCapacity;
        this.isRoot = isRoot;
        this.auto = auto;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     * @param valueType The value type.
     */
    public StringBMapJidFactory(String actorType, String valueType) {
        super(actorType);
        this.valueType = valueType;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param valueType    The value type.
     * @param nodeCapacity The size of the nodes.
     * @param isRoot       Create a root node when true.
     * @param auto         Define the node as a leaf when true.
     */
    public StringBMapJidFactory(String actorType, String valueType,
                                int nodeCapacity, boolean isRoot, boolean auto) {
        super(actorType);
        this.valueType = valueType;
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
        return new StringBMapJid();
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
        StringBMapJid imj = (StringBMapJid) super.newActor(mailbox, parent);
        if (valueFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            valueFactory = f.getActorFactory(valueType);
        }
        imj.valueFactory = valueFactory;
        imj.nodeCapacity = nodeCapacity;
        imj.isRoot = isRoot;
        imj.init();
        if (auto)
            imj.setNodeType("leaf");
        return imj;
    }
}
