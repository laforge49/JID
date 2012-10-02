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
package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.*;

/**
 * A base class for applications, AppJid provides a durable tuple without an external interface.
 */
public class AppJid extends Jid {
    /**
     * The size of the serialized data (exclusive of its length header).
     */
    private int _len;

    /**
     * An array of actor types, one for each element in the tuple.
     */
    protected ActorFactory[] tupleFactories;

    /**
     * A tuple of actors.
     */
    protected _Jid[] tuple;

    /**
     * Returns the element factories.
     *
     * @return An array of element factories.
     */
    protected ActorFactory[] getTupleFactories()
            throws Exception {
        if (tupleFactories != null)
            return tupleFactories;
        throw new IllegalStateException("tupleFactories is null");
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    protected void _iSetBytes(int i, byte[] bytes)
            throws Exception {
        _initialize();
        Jid elementJid = createSubordinate(tupleFactories[i], bytes);
        _Jid oldElementJid = _iGet(i);
        oldElementJid.setContainerJid(null);
        tuple[i] = elementJid;
        change(elementJid.getSerializedLength() - oldElementJid.getSerializedLength());
    }

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    protected int _size()
            throws Exception {
        return getTupleFactories().length;
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component, or null if the index is out of range.
     */
    protected _Jid _iGet(int i) throws Exception {
        _initialize();
        if (i < 0)
            i += _size();
        if (i < 0 || i >= _size())
            return null;
        return tuple[i];
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    protected _Jid _resolvePathname(String pathname)
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
        int n = 0;
        try {
            n = Integer.parseInt(ns);
        } catch (Exception ex) {
            throw new IllegalArgumentException("pathname " + pathname);
        }
        if (n < 0 || n >= _size())
            throw new IllegalArgumentException("pathname " + pathname);
        _Jid jid = _iGet(n);
        if (s == pathname.length())
            return jid;
        return jid.resolvePathname(pathname.substring(s + 1));
    }

    /**
     * Perform lazy initialization.
     *
     * @throws Exception Any exceptions thrown during initialization.
     */
    private void _initialize()
            throws Exception {
        if (tuple != null)
            return;
        tupleFactories = getTupleFactories();
        ReadableBytes readableBytes = null;
        if (isSerialized()) {
            readableBytes = readable();
            _skipLen(readableBytes);
        }
        tuple = new _Jid[_size()];
        int i = 0;
        _len = 0;
        while (i < _size()) {
            Jid elementJid = createSubordinate(tupleFactories[i], readableBytes);
            _len += elementJid.getSerializedLength();
            tuple[i] = elementJid;
            i += 1;
        }
    }

    /**
     * Skip over the length at the beginning of the serialized data.
     *
     * @param readableBytes Holds the serialized data.
     */
    private void _skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }

    /**
     * Returns the size of the serialized data (exclusive of its length header).
     *
     * @param readableBytes Holds the serialized data.
     * @return The size of the serialized data (exclusive of its length header).
     */
    private int _loadLen(ReadableBytes readableBytes) {
        return readableBytes.readInt();
    }

    /**
     * Writes the size of the serialized data (exclusive of its length header).
     *
     * @param appendableBytes The object written to.
     */
    private void _saveLen(AppendableBytes appendableBytes) {
        appendableBytes.writeInt(_len);
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength()
            throws Exception {
        _initialize();
        return Util.INT_LENGTH + _len;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
        _saveLen(appendableBytes);
        int i = 0;
        while (i < _size()) {
            _iGet(i).save(appendableBytes);
            i += 1;
        }
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes)
            throws Exception {
        super.load(readableBytes);
        _len = _loadLen(readableBytes);
        tuple = null;
        readableBytes.skip(_len);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(int lengthChange)
            throws Exception {
        _len += lengthChange;
        super.change(lengthChange);
    }
}
