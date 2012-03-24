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
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.flenc.ActorTypes;
import org.agilewiki.jid.collection.vlenc.GetActorsType;
import org.agilewiki.jid.collection.vlenc.ListJidC;
import org.agilewiki.jid.scalar.Scalar;

/**
 * Holds a map.
 */
abstract public class MapJidC<KEY_TYPE extends Comparable>
        extends ListJidC
        implements ActorTypes {
    /**
     * Returns the actor type of the key.
     *
     * @return The actor type of the key.
     */
    abstract protected String getKeyType();

    abstract KEY_TYPE stringToKey(String key);

    /**
     * Returns the actor type of all the elements in the list.
     *
     * @return The actor type of all the elements in the list.
     */
    final protected String getActorsType()
            throws Exception {
        return JidFactories.ACTOR_JID_ATYPE;
    }

    /**
     * Returns the array of actor types used to define the key/value tuples.
     *
     * @return The array of actor types.
     */
    final public String[] getActorTypes()
            throws Exception {
        String[] actorTypes = new String[2];
        actorTypes[0] = getKeyType();
        actorTypes[1] = getActorsType();
        return actorTypes;
    }

    /**
     * Returns the actor type of the values in the map.
     *
     * @return The actor type of the values in the list.
     */
    protected String getValuesType()
            throws Exception {
        return GetActorsType.req.call(thisActor);
    }

    /**
     * Locate the tuple with a matching first element.
     *
     * @param key The key which matches to the tuple's first element.
     * @return The index or - (insertion point + 1).
     */
    final protected int search(KEY_TYPE key) throws Exception {
        int low = 0;
        int high = size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            ComparableKey<KEY_TYPE> midVal = (ComparableKey<KEY_TYPE>) list.get(mid);
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
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key Used to match the first element of the tuples.
     * @return True if a new tuple was created.
     */
    final protected Boolean kMake(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i > -1)
            return false;
        i = -i - 1;
        iAdd(i);
        Collection t = (Collection) get(i);
        Scalar<KEY_TYPE, KEY_TYPE> e0 = (Scalar<KEY_TYPE, KEY_TYPE>) t.get(0);
        e0.setValue(key);
        return true;
    }

    /**
     * Returns the JID value associated with the key.
     *
     * @param key The key.
     * @return The jid assigned to the key, or null.
     */
    final protected Jid get(KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i < 0)
            return null;
        Collection t = (Collection) get(i);
        return t.get(1);
    }

    /**
     * Returns the Actor value associated with the key.
     *
     * @param key The key.
     * @return The actor assigned to the key, or null.
     */
    final protected Actor kGet(KEY_TYPE key)
            throws Exception {
        Jid jid = get(key);
        if (jid == null)
            return null;
        return jid.thisActor();
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    final public Actor resolvePathname(String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return thisActor;
        }
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        Jid jid = get(stringToKey(ns));
        if (jid == null)
            return null;
        if (s == pathname.length())
            return jid.thisActor();
        return jid.resolvePathname(pathname.substring(s + 1));
    }
}
