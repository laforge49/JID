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

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;
import org.agilewiki.jid.JidFactory;

/**
 * Assigns a value if not already present.
 */
public class MakeActor
        extends Request<Boolean, Reference> {
    /**
     * The actor type.
     */
    private String actorType;

    /**
     * The jid factory.
     */
    private JidFactory jidFactory;

    /**
     * Returns the actor type.
     *
     * @return The actor type.
     */
    public String getValue() {
        return actorType;
    }

    /**
     * Returns the jid factory.
     *
     * @return The jid factory.
     */
    public JidFactory getJidFactory() {
        return jidFactory;
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
     * Creates the request.
     *
     * @param jidFactory The jid factory.
     */
    public MakeActor(JidFactory jidFactory) {
        if (jidFactory == null)
            throw new IllegalArgumentException("value may not be null");
        this.jidFactory = jidFactory;
    }

    /**
     * Returns true when targetActor is an instanceof TARGET_TYPE
     *
     * @param targetActor The actor to be called.
     * @return True when targetActor is an instanceof TARGET_TYPE.
     */
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof Reference;
    }

    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        if (actorType != null)
            rp.processResponse(((Reference) targetActor).makeValue(actorType));
        else
            rp.processResponse(((Reference) targetActor).makeValue(jidFactory));
    }
}
