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
package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.factory.ActorFactory;

/**
 * Creates a UnionJid.
 */
public class UnionJidFactory extends ActorFactory {
    private ActorFactory[] unionFactories;

    /**
     * Create a JLPCActorFactory.
     *
     * @param subActorType   The actor type.
     * @param unionFactories The element factories.
     */
    public UnionJidFactory(String subActorType, ActorFactory... unionFactories) {
        super(subActorType);
        this.unionFactories = unionFactories;
    }

    /**
     * Initialize a new UnionJid with its element factories.
     *
     * @param tj The new union.
     */
    public void assignElementFactories(UnionJid tj) {
        tj.unionFactories = unionFactories;
    }

    /**
     * Create a JLPCActor.
     *
     * @return The new actor.
     */
    @Override
    protected UnionJid instantiateActor()
            throws Exception {
        UnionJid uj = new UnionJid();
        assignElementFactories(uj);
        return uj;
    }
}
