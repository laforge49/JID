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

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.vlenc.ListJid;

/**
 * Holds a map.
 */
abstract public class MapJid<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE extends Jid>
        extends ListJid<MapEntry<KEY_TYPE, VALUE_TYPE>>
        implements JAMap<KEY_TYPE, VALUE_TYPE>, Collection<MapEntry<KEY_TYPE, VALUE_TYPE>> {

    public ActorFactory valueFactory;

    /**
     * Returns the JidFactory for the key.
     *
     * @return The JidFactory for the key.
     */
    abstract protected ActorFactory getKeyFactory();

    /**
     * Converts a string to a key.
     *
     * @param skey The string to be converted.
     * @return The key.
     */
    abstract protected KEY_TYPE stringToKey(String skey);

    /**
     * Returns the actor type of all the elements in the list.
     *
     * @return The actor type of all the elements in the list.
     */
    @Override
    final protected ActorFactory getEntryFactory()
            throws Exception {
        return new MapEntryFactory(null, getKeyFactory(), getValueFactory());
    }

    /**
     * Returns the JidFactory for the values in the map.
     *
     * @return The actor type of the values in the list.
     */
    protected ActorFactory getValueFactory()
            throws Exception {
        if (valueFactory != null)
            return valueFactory;
        throw new IllegalStateException("valueFactory not set");
    }

    /**
     * Locate the entry with a matching first element.
     *
     * @param key The key which matches to the entry's first element.
     * @return The index or - (insertion point + 1).
     */
    final public int search(KEY_TYPE key)
            throws Exception {
        initializeList();
        int low = 0;
        int high = size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            MapEntry<KEY_TYPE, VALUE_TYPE> midVal = iGet(mid);
            int c = midVal.compareKeyTo(key);
            if (c < 0)
                low = mid + 1;
            else if (c > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -(low + 1);
    }

    /**
     * Locate the entry with a higher key.
     *
     * @param key The key which matches to the entry's first element.
     * @return The index or -1.
     */
    final public int higher(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i > -1)
            i += 1;
        else {
            i = -i - 1;
        }
        if (i == size())
            return -1;
        return i;
    }

    /**
     * Locate the entry with the first element >= a key, or the last entry.
     *
     * @param key The key which matches to the entry's first element, or size.
     * @return The index, or size.
     */
    final public int match(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i > -1)
            return i;
        i = -i - 1;
        return i;
    }

    /**
     * Locate the entry with the first element >= a key.
     *
     * @param key The key which matches to the entry's first element.
     * @return The index or -1.
     */
    final public int ceiling(KEY_TYPE key)
            throws Exception {
        int i = match(key);
        if (i == size())
            return -1;
        return i;
    }

    /**
     * Add an entry to the map unless there is an entry with a matching first element.
     *
     * @param key Used to match the first element of the entries.
     * @return True if a new entry was created.
     */
    @Override
    final public Boolean kMake(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i > -1)
            return false;
        i = -i - 1;
        iAdd(i);
        MapEntry<KEY_TYPE, VALUE_TYPE> me = iGet(i);
        me.setKey(key);
        return true;
    }

    /**
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key   Used to match the first element of the tuples.
     * @param bytes The serialized form of a JID of the appropriate type.
     * @return True if a new tuple was created; otherwise the old value is unaltered.
     */
    public Boolean kMakeBytes(KEY_TYPE key, byte[] bytes)
            throws Exception {
        if (!kMake(key))
            return false;
        kSetBytes(key, bytes);
        return true;
    }

    final public MapEntry<KEY_TYPE, VALUE_TYPE> kGetEntry(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i < 0)
            return null;
        return iGet(i);
    }

    /**
     * Returns the JID value associated with the key.
     *
     * @param key The key.
     * @return The jid assigned to the key, or null.
     */
    @Override
    final public VALUE_TYPE kGet(KEY_TYPE key)
            throws Exception {
        MapEntry<KEY_TYPE, VALUE_TYPE> entry = kGetEntry(key);
        if (entry == null)
            return null;
        return entry.getValue();
    }

    /**
     * Returns the JID value with a greater key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    @Override
    final public MapEntry<KEY_TYPE, VALUE_TYPE> getHigher(KEY_TYPE key)
            throws Exception {
        int i = higher(key);
        if (i < 0)
            return null;
        return iGet(i);
    }

    /**
     * Returns the JID value with the smallest key >= the given key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    @Override
    final public MapEntry<KEY_TYPE, VALUE_TYPE> getCeiling(KEY_TYPE key)
            throws Exception {
        int i = ceiling(key);
        if (i < 0)
            return null;
        return iGet(i);
    }

    /**
     * Removes the item identified by the key.
     *
     * @param key The key.
     * @return True when the item was present and removed.
     */
    @Override
    final public boolean kRemove(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i < 0)
            return false;
        iRemove(i);
        return true;
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    final public _Jid resolvePathname(String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return this;
        }
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        _Jid jid = kGet(stringToKey(ns));
        if (jid == null)
            return null;
        if (s == pathname.length())
            return jid;
        return jid.resolvePathname(pathname.substring(s + 1));
    }

    public MapEntry<KEY_TYPE, VALUE_TYPE> getFirst()
            throws Exception {
        return iGet(0);
    }

    public MapEntry<KEY_TYPE, VALUE_TYPE> getLast()
            throws Exception {
        return iGet(-1);
    }

    public KEY_TYPE getLastKey()
            throws Exception {
        return getLast().getKey();
    }

    @Override
    public void kSetBytes(KEY_TYPE key, byte[] bytes)
            throws Exception {
        MapEntry<KEY_TYPE, VALUE_TYPE> entry = kGetEntry(key);
        if (entry == null)
            throw new IllegalArgumentException("not present: " + key);
        entry.setValueBytes(bytes);
    }
}
