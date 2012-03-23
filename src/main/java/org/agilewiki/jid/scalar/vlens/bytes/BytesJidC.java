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
package org.agilewiki.jid.scalar.vlens.bytes;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.scalar.vlens.VLenScalarJidC;

/**
 * A JID component that holds a byte array.
 */
public class BytesJidC
        extends VLenScalarJidC<byte[], byte[], byte[]> {
    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(GetBytes.class.getName(),
                new SynchronousMethodBinding<GetBytes, byte[]>() {
                    @Override
                    public byte[] synchronousProcessRequest(Internals internals,
                                                            GetBytes request)
                            throws Exception {
                        return getValue();
                    }
                });

        thisActor.bind(SetBytes.class.getName(),
                new VoidSynchronousMethodBinding<SetBytes>() {
                    @Override
                    public void synchronousProcessRequest(Internals internals,
                                                          SetBytes request)
                            throws Exception {
                        setValue(request.getValue());
                    }
                });

        thisActor.bind(MakeBytes.class.getName(),
                new SynchronousMethodBinding<MakeBytes, Boolean>() {
                    @Override
                    public Boolean synchronousProcessRequest(Internals internals,
                                                             MakeBytes request)
                            throws Exception {
                        return makeValue(request.getValue());
                    }
                });
    }

    /**
     * Assign a value.
     *
     * @param v The new value.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void setValue(byte[] v) throws Exception {
        int c = v.length;
        if (len > -1)
            c -= len;
        value = v;
        serializedBytes = null;
        serializedOffset = -1;
        change(c);
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param v The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(byte[] v) throws Exception {
        if (len > -1)
            return false;
        int c = v.length;
        if (len > -1)
            c -= len;
        value = v;
        serializedBytes = null;
        serializedOffset = -1;
        change(c);
        return true;
    }

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component, or null.
     */
    public byte[] getValue() {
        if (len == -1)
            return null;
        if (value != null)
            return value;
        ReadableBytes readableBytes = readable();
        skipLen(readableBytes);
        value = readableBytes.readBytes(len);
        return value;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        saveLen(appendableBytes);
        if (len == -1)
            return;
        appendableBytes.writeBytes(value);
    }
}
