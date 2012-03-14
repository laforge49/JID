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
package org.agilewiki.jid.scalar.vlens;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.scalar.ScalarJidC;

/**
 * A JID component that holds a variable-length value, or null.
 */
abstract public class VLenScalarJidC<VALUE_TYPE, SET_TYPE, RESPONSE_TYPE>
        extends ScalarJidC<SET_TYPE, RESPONSE_TYPE> {

    /**
     * Holds the value, or null.
     */
    protected VALUE_TYPE value = null;

    /**
     * The size of the serialized (exclusive of its length header).
     */
    protected int len = -1;

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(Clear.class.getName(),
                new VoidSynchronousMethodBinding<Clear<VALUE_TYPE, SET_TYPE, RESPONSE_TYPE>>() {
                    @Override
                    public void synchronousProcessRequest(Internals internals, Clear request)
                            throws Exception {
                        clear();
                    }
                });

        thisActor.bind(MakeValue.class.getName(),
                new SynchronousMethodBinding<MakeValue<VALUE_TYPE, SET_TYPE, RESPONSE_TYPE>, Boolean>() {
                    @Override
                    public Boolean synchronousProcessRequest(Internals internals,
                                                             MakeValue<VALUE_TYPE, SET_TYPE, RESPONSE_TYPE> request)
                            throws Exception {
                        return makeValue(request.getValue());
                    }
                });
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param v The value.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    abstract protected Boolean makeValue(SET_TYPE v)
            throws Exception;

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    protected void clear() throws Exception {
        if (len == -1)
            return;
        int l = len;
        value = null;
        serializedBytes = null;
        serializedOffset = -1;
        change(-l);
        len = -1;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        if (len == -1)
            return Util.INT_LENGTH;
        return Util.INT_LENGTH + len;
    }

    /**
     * Returns the size of the serialized data (exclusive of its length header).
     *
     * @param readableBytes Holds the serialized data.
     * @return The size of the serialized data (exclusive of its length header).
     */
    protected int loadLen(ReadableBytes readableBytes) {
        return readableBytes.readInt();
    }

    /**
     * Writes the size of the serialized data (exclusive of its length header).
     *
     * @param appendableBytes The object written to.
     */
    protected void saveLen(AppendableBytes appendableBytes) {
        appendableBytes.writeInt(len);
    }

    /**
     * Skip over the length at the beginning of the serialized data.
     *
     * @param readableBytes Holds the serialized data.
     */
    protected void skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(int lengthChange) throws Exception {
        if (len == -1)
            len = lengthChange;
        else
            len += lengthChange;
        super.change(lengthChange);
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
        value = null;
        if (len > -1)
            readableBytes.skip(len);
    }
}
