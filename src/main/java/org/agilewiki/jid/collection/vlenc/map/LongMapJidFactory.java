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
 * Creates LongMapJid's.
 */
public class LongMapJidFactory extends ActorFactory {
    private ActorFactory valueFactory;
    private String valueType;
    private int initialCapacity = 10;

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param valueFactory    The value factory.
     * @param initialCapacity The initial capacity.
     */
    public LongMapJidFactory(String actorType, ActorFactory valueFactory, int initialCapacity) {
        super(actorType);
        this.valueFactory = valueFactory;
        this.initialCapacity = initialCapacity;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType    The actor type.
     * @param valueFactory The value factory.
     */
    public LongMapJidFactory(String actorType, ActorFactory valueFactory) {
        super(actorType);
        this.valueFactory = valueFactory;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType       The actor type.
     * @param valueType       The value type.
     * @param initialCapacity The initial capacity.
     */
    public LongMapJidFactory(String actorType, String valueType, int initialCapacity) {
        super(actorType);
        this.valueType = valueType;
        this.initialCapacity = initialCapacity;
    }

    /**
     * Create an ActorFactory.
     *
     * @param actorType The actor type.
     * @param valueType The value type.
     */
    public LongMapJidFactory(String actorType, String valueType) {
        super(actorType);
        this.valueType = valueType;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected LongMapJid instantiateActor()
            throws Exception {
        return new LongMapJid();
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
        LongMapJid imj = (LongMapJid) super.newActor(mailbox, parent);
        if (valueFactory == null) {
            Factory f = (Factory) parent.getMatch(Factory.class);
            valueFactory = f.getActorFactory(valueType);
        }
        imj.valueFactory = valueFactory;
        imj.initialCapacity = initialCapacity;
        return imj;
    }
}
