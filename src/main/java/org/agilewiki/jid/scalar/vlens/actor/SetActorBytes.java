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
import org.agilewiki.jactor.lpc.SynchronousRequest;
import org.agilewiki.jid.JidFactory;

/**
 * Creates a JID actor and loads its serialized data.
 */
final public class SetActorBytes
        extends SynchronousRequest<Object, ActorJid> {
    /**
     * An actor type name.
     */
    private String actorType;

    /**
     * The jid factory.
     */
    private JidFactory jidFactory;

    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * Create the request.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     */
    public SetActorBytes(String actorType, byte[] bytes) {
        if (actorType == null)
            throw new IllegalArgumentException("value may not be null");
        this.actorType = actorType;
        this.bytes = bytes;
    }

    /**
     * Creates the request.
     *
     * @param jidFactory The jid factory.
     * @param bytes      The serialized data.
     */
    public SetActorBytes(JidFactory jidFactory, byte[] bytes) {
        if (jidFactory == null)
            throw new IllegalArgumentException("value may not be null");
        this.jidFactory = jidFactory;
        this.bytes = bytes;
    }

    /**
     * Returns an actor type name.
     *
     * @return An actor type name.
     */
    public String getActorType() {
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
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Send a synchronous request.
     *
     * @param targetActor The target actor.
     * @return The response.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected Boolean _call(ActorJid targetActor)
            throws Exception {
        if (actorType != null)
            targetActor.setJidBytes(actorType, bytes);
        else
            targetActor.setJidBytes(jidFactory, bytes);
        return null;
    }

    /**
     * Returns true when targetActor is an instanceof TARGET_TYPE
     *
     * @param targetActor The actor to be called.
     * @return True when targetActor is an instanceof TARGET_TYPE.
     */
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof ActorJid;
    }
}
