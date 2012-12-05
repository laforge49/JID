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

import org.agilewiki.jid.Jid;
import org.agilewiki.jid._Jid;

/**
 * Holds a map.
 */
public interface JAMap<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE extends Jid>
        extends _Jid {

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    public int size()
            throws Exception;

    /**
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key Used to match the first element of the tuples.
     * @return True if a new tuple was created.
     */
    public Boolean kMake(KEY_TYPE key)
            throws Exception;

    /**
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key   Used to match the first element of the tuples.
     * @param bytes The serialized form of a JID of the appropriate type.
     * @return True if a new tuple was created; otherwise the old value is unaltered.
     */
    public Boolean kMakeBytes(KEY_TYPE key, byte[] bytes)
            throws Exception;

    /**
     * Returns the Actor value associated with the key.
     *
     * @param key The key.
     * @return The actor assigned to the key, or null.
     */
    public VALUE_TYPE kGet(KEY_TYPE key)
            throws Exception;

    /**
     * Returns the Actor value with a greater key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    public MapEntry<KEY_TYPE, VALUE_TYPE> getHigher(KEY_TYPE key)
            throws Exception;

    /**
     * Returns the Actor value with the smallest key >= the given key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    public MapEntry<KEY_TYPE, VALUE_TYPE> getCeiling(KEY_TYPE key)
            throws Exception;

    /**
     * Removes the item identified by the key.
     *
     * @param key The key.
     * @return True when the item was present and removed.
     */
    public boolean kRemove(KEY_TYPE key)
            throws Exception;

    public MapEntry<KEY_TYPE, VALUE_TYPE> getFirst()
            throws Exception;

    public MapEntry<KEY_TYPE, VALUE_TYPE> getLast()
            throws Exception;

    public void kSetBytes(KEY_TYPE key, byte[] bytes)
            throws Exception;
}
