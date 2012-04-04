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
package org.agilewiki.jid.collection;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.SynchronousRequest;

/**
 * Creates a JID actor in place of the ith element of the collection and loads its serialized data.
 */
public class ISetBytes extends SynchronousRequest<Object, CollectionJid> {
    /**
     * The index of the desired element.
     */
    private int i;

    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * Create the request
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     */
    public ISetBytes(int i, byte[] bytes) {
        this.i = i;
        this.bytes = bytes;
    }

    /**
     * Returns the index of the desired element.
     *
     * @return The index of the desired element.
     */
    public int getI() {
        return i;
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
    protected Object _call(CollectionJid targetActor)
            throws Exception {
        targetActor.iSetBytes(i, bytes);
        return null;
    }

    /**
     * Returns true when targetActor is an instanceof TARGET_TYPE
     *
     * @param targetActor The actor to be called.
     * @return True when targetActor is an instanceof TARGET_TYPE.
     */
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof CollectionJid;
    }
}
