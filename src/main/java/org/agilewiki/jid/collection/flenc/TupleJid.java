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

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.*;
import org.agilewiki.jid.collection.CollectionJid;

/**
 * Holds a fixed-size array of JID actors of various types.
 */
public class TupleJid
        extends CollectionJid
        implements ComparableKey<Object> {
    /**
     * An array of actor types, one for each element in the tuple.
     */
    protected JidFactory[] tupleFactories;

    /**
     * A tuple of actors.
     */
    protected _Jid[] tuple;

    /**
     * Create a TupleJid
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public TupleJid(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Perform lazy initialization.
     *
     * @throws Exception Any exceptions thrown during initialization.
     */
    private void initialize()
            throws Exception {
        if (tupleFactories != null)
            return;
        tupleFactories = GetTupleFactories.req.call(this);
        ReadableBytes readableBytes = null;
        if (isSerialized()) {
            readableBytes = readable();
            skipLen(readableBytes);
        }
        tuple = new _Jid[size()];
        int i = 0;
        len = 0;
        while (i < size()) {
            Jid elementJid = createSubordinate(tupleFactories[i], readableBytes);
            //_Jid elementJid = tupleFactories[i].newJID(getMailbox(), getParent(), this, readableBytes);
            len += elementJid.getSerializedLength();
            tuple[i] = elementJid;
            i += 1;
        }
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
        initialize();
        Jid elementJid = createSubordinate(tupleFactories[i], bytes);
        _Jid oldElementJid = iGetJid(i);
        oldElementJid.setContainerJid(null);
        tuple[i] = elementJid;
        change(elementJid.getSerializedLength() - oldElementJid.getSerializedLength());
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength()
            throws Exception {
        initialize();
        return Util.INT_LENGTH + len;
    }

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    public int size()
            throws Exception {
        initialize();
        return tupleFactories.length;
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    public _Jid iGetJid(int i) throws Exception {
        initialize();
        return tuple[i];
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
        int i = 0;
        while (i < size()) {
            iGetJid(i).save(appendableBytes);
            i += 1;
        }
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        len = loadLen(readableBytes);
        tuple = null;
        readableBytes.skip(len);
    }

    /**
     * Compares element 0
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o) using element 0.
     */
    public int compareKeyTo(Object o)
            throws Exception {
        ComparableKey<Object> e0 = (ComparableKey<Object>) iGetJid(0);
        return e0.compareKeyTo(o);
    }
}
