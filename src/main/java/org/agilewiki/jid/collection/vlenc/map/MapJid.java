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
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.flenc.GetTupleFactories;
import org.agilewiki.jid.collection.flenc.TupleFactories;
import org.agilewiki.jid.collection.flenc.TupleJidFactory;
import org.agilewiki.jid.collection.vlenc.GetValueFactory;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.jidFactory._JidFactory;
import org.agilewiki.jid.scalar.Scalar;

/**
 * Holds a map.
 */
abstract public class MapJid<KEY_TYPE extends Comparable>
        extends ListJid
        implements TupleFactories, Map<KEY_TYPE> {

    private _JidFactory[] tupleFactories;

    /**
     * Create a MapJid
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    protected MapJid(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Returns the JidFactory for the key.
     *
     * @return The JidFactory for the key.
     */
    abstract protected _JidFactory getKeyFactory();

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
    final protected _JidFactory getListFactory()
            throws Exception {
        return new TupleJidFactory();
    }

    /**
     * Returns the array of actor types used to define the key/value tuples.
     *
     * @return The array of actor types.
     */
    @Override
    final public _JidFactory[] getTupleFactories()
            throws Exception {
        if (tupleFactories != null)
            return tupleFactories;
        tupleFactories = new _JidFactory[2];
        tupleFactories[0] = getKeyFactory();
        tupleFactories[1] = getValueFactory();
        return tupleFactories;
    }

    /**
     * Returns the JidFactory for the values in the map.
     *
     * @return The actor type of the values in the list.
     */
    protected _JidFactory getValueFactory()
            throws Exception {
        return GetValueFactory.req.call(this);
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
    @Override
    final public Boolean kMake(KEY_TYPE key)
            throws Exception {
        initialize();
        int i = search(key);
        if (i > -1)
            return false;
        i = -i - 1;
        iAdd(i);
        Collection t = (Collection) iGetJid(i);
        Scalar<KEY_TYPE, KEY_TYPE> e0 = (Scalar<KEY_TYPE, KEY_TYPE>) t.iGetJid(0);
        e0.setValue(key);
        return true;
    }

    /**
     * Returns the JID value associated with the key.
     *
     * @param key The key.
     * @return The jid assigned to the key, or null.
     */
    @Override
    final public _Jid kGetJid(KEY_TYPE key)
            throws Exception {
        initialize();
        int i = search(key);
        if (i < 0)
            return null;
        Collection t = (Collection) iGetJid(i);
        return t.iGetJid(1);
    }

    /**
     * Returns the Actor value associated with the key.
     *
     * @param key The key.
     * @return The actor assigned to the key, or null.
     */
    @Override
    final public Actor kGet(KEY_TYPE key)
            throws Exception {
        _Jid jid = kGetJid(key);
        if (jid == null)
            return null;
        return jid.thisActor();
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp)
            throws Exception {
        if (request instanceof GetTupleFactories) {
            rp.processResponse(getTupleFactories());
        } else if (request instanceof KMake) {
            rp.processResponse(kMake(((KMake<KEY_TYPE>) request).getKey()));
        } else if (request instanceof KGet) {
            rp.processResponse(kGet(((KGet<KEY_TYPE>) request).getKey()));
        } else super.processRequest(request, rp);
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
        initialize();
        if (pathname.length() == 0) {
            return this;
        }
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        _Jid jid = kGetJid(stringToKey(ns));
        if (jid == null)
            return null;
        if (s == pathname.length())
            return jid.thisActor();
        return jid.resolvePathname(pathname.substring(s + 1));
    }
}
