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

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.scalar.vlens.VLenScalarJidA;

/**
 * A JID component that holds a byte array.
 */
public class BytesJidA
        extends VLenScalarJidA<byte[], byte[], byte[]> {
    /**
     * Create a StringJidA.
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public BytesJidA(Mailbox mailbox) {
        super(mailbox);
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
        if (request instanceof GetBytes)
            rp.processResponse(getValue());
        else if (request instanceof SetBytes) {
            setValue(((SetBytes) request).getValue());
            rp.processResponse(null);
        } else if (request instanceof MakeBytes) {
            rp.processResponse(makeValue(((MakeBytes) request).getValue()));
        } else super.processRequest(request, rp);
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
