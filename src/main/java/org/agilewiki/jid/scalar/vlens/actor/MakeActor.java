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

import org.agilewiki.jactor.bind.JLPCSynchronousRequest;

/**
 * Assigns a value if not already present.
 */
public class MakeActor
        extends JLPCSynchronousRequest<Boolean, ActorJidA> {
    /**
     * The actor type.
     */
    private String actorType;

    /**
     * Returns the actor type.
     *
     * @return The actor type.
     */
    public String getValue() {
        return actorType;
    }

    /**
     * Creates the request.
     *
     * @param actorType The actor type.
     */
    public MakeActor(String actorType) {
        if (actorType == null)
            throw new IllegalArgumentException("value may not be null");
        this.actorType = actorType;
    }

    /**
     * Send a synchronous request.
     *
     * @param targetActor The target actor.
     * @return The response.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected Boolean call(ActorJidA targetActor)
            throws Exception {
        return targetActor.makeValue(actorType);
    }
}
