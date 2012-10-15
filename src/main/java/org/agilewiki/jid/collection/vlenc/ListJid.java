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
package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.*;
import org.agilewiki.jid.collection.CollectionJid;

import java.util.ArrayList;

/**
 * Holds an ArrayList of JID actors, all of the same type.
 */
public class ListJid<ENTRY_TYPE extends Jid>
        extends CollectionJid<ENTRY_TYPE> implements JAList {
    public int initialCapacity = 10;

    /**
     * Actor type of the elements.
     */
    protected ActorFactory entryFactory;

    /**
     * A list of JID actors.
     */
    protected ArrayList<ENTRY_TYPE> list;

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    @Override
    public int size()
            throws Exception {
        initializeList();
        return list.size();
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     *          If negative, the index used is increased by the size of the collection,
     *          so that -1 returns the last element.
     * @return The ith JID component, or null if the index is out of range.
     */
    @Override
    public ENTRY_TYPE iGet(int i)
            throws Exception {
        initializeList();
        if (i < 0)
            i += list.size();
        if (i < 0 || i >= list.size())
            return null;
        return list.get(i);
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH * 2 + len;
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
        len = loadLen(readableBytes);
        list = null;
        readableBytes.skip(Util.INT_LENGTH + len);
    }

    /**
     * Returns the JidFactory for all the elements in the list.
     *
     * @return The JidFactory for of all the elements in the list.
     */
    protected ActorFactory getEntryFactory()
            throws Exception {
        if (entryFactory == null)
            throw new IllegalStateException("entryFactory uninitialized");
        return entryFactory;
    }

    /**
     * Perform lazy initialization.
     *
     * @throws Exception Any exceptions thrown during initialization.
     */
    protected void initializeList()
            throws Exception {
        if (list != null)
            return;
        entryFactory = getEntryFactory();
        if (!isSerialized()) {
            list = new ArrayList<ENTRY_TYPE>();
            return;
        }
        ReadableBytes readableBytes = readable();
        skipLen(readableBytes);
        int count = readableBytes.readInt();
        list = new ArrayList<ENTRY_TYPE>(count);
        int i = 0;
        while (i < count) {
            ENTRY_TYPE elementJid = (ENTRY_TYPE) createSubordinate(entryFactory, this, readableBytes);
            list.add(elementJid);
            i += 1;
        }
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
        saveLen(appendableBytes);
        appendableBytes.writeInt(size());
        int i = 0;
        while (i < size()) {
            iGet(i).save(appendableBytes);
            i += 1;
        }
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public _Jid resolvePathname(String pathname)
            throws Exception {
        initializeList();
        return super.resolvePathname(pathname);
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    @Override
    public void iSetBytes(int i, byte[] bytes)
            throws Exception {
        initializeList();
        if (i < 0)
            i += list.size();
        if (i < 0 || i >= list.size())
            throw new IllegalArgumentException();
        ENTRY_TYPE elementJid = (ENTRY_TYPE) createSubordinate(entryFactory, this, bytes);
        ENTRY_TYPE oldElementJid = iGet(i);
        oldElementJid.setContainerJid(null);
        list.set(i, elementJid);
        change(elementJid.getSerializedLength() -
                oldElementJid.getSerializedLength());
    }

    @Override
    public void iAddBytes(int i, byte[] bytes)
            throws Exception {
        initializeList();
        if (i < 0)
            i = size() + 1 + i;
        ENTRY_TYPE jid = (ENTRY_TYPE) createSubordinate(entryFactory, this, bytes);
        int c = jid.getSerializedLength();
        list.add(i, jid);
        change(c);
    }

    @Override
    public void iAdd(int i)
            throws Exception {
        initializeList();
        if (i < 0)
            i = size() + 1 + i;
        ENTRY_TYPE jid = (ENTRY_TYPE) createSubordinate(entryFactory, this);
        int c = jid.getSerializedLength();
        list.add(i, jid);
        change(c);
    }

    @Override
    public void empty()
            throws Exception {
        int c = 0;
        int i = 0;
        int s = size();
        while (i < s) {
            _Jid jid = iGet(i);
            jid.setContainerJid(null);
            c -= jid.getSerializedLength();
            i += 1;
        }
        list.clear();
        change(c);
    }

    @Override
    public void iRemove(int i)
            throws Exception {
        int s = size();
        if (i < 0)
            i += s;
        if (i < 0 || i >= s)
            throw new IllegalArgumentException();
        _Jid jid = iGet(i);
        jid.setContainerJid(null);
        int c = -jid.getSerializedLength();
        list.remove(i);
        change(c);
    }
}
