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
package org.agilewiki.jid.scalar.vlen.jidjid;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Creates a JID actor and loads its serialized data.
 */
final public class SetBytes extends SynchronousRequest<Object> {
    /**
     * An actor type name.
     */
    private String actorType;

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
    public SetBytes(String actorType, byte[] bytes) {
        this.actorType = actorType;
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
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public byte[] getBytes() {
        return bytes;
    }
}
