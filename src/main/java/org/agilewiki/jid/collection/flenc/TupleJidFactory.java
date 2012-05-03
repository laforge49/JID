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
package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates a TupleJid.
 */
public class TupleJidFactory extends ActorFactory {
    private String subActorType;
    private ActorFactory[] tupleFactories;

    /**
     * Create a JLPCActorFactory.
     *
     * @param subActorType   The actor type.
     * @param tupleFactories The element factories.
     */
    public TupleJidFactory(String subActorType, ActorFactory... tupleFactories) {
        super(subActorType);
        this.tupleFactories = tupleFactories;
    }

    /**
     * Initialize a new tuple with its element factories.
     *
     * @param tj The new tuple.
     */
    public void assignElementFactories(TupleJid tj) {
        tj.tupleFactories = tupleFactories;
    }

    /**
     * Create a JLPCActor.
     *
     * @param mailbox The mailbox of the new actor.
     * @return The new actor.
     */
    @Override
    protected TupleJid instantiateActor(Mailbox mailbox)
            throws Exception {
        TupleJid tj = new TupleJid(mailbox);
        assignElementFactories(tj);
        return tj;
    }
}
