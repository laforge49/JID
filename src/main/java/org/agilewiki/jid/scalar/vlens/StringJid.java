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
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.scalar.GetValue;
import org.agilewiki.jid.scalar.SetValue;

/**
 * A JID component that holds a String.
 */
public class StringJid
        extends VLenScalarJid<String, String>
        implements ComparableKey<String> {
    /**
     * The GetValue request.
     */
    public static final GetValue<String> getValueReq = (GetValue<String>) GetValue.req;

    /**
     * Returns the MakeValue request.
     *
     * @param value The value.
     * @return The MakeValue request.
     */
    public static final MakeValue makeValueReq(String value) {
        return new MakeValue(value);
    }

    /**
     * Returns the SetValue request.
     *
     * @param value The value.
     * @return The SetValue request.
     */
    public static final SetValue setValueReq(String value) {
        return new SetValue(value);
    }

    /**
     * Assign a value.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void setValue(Internals internals, SetValue request) throws Exception {
        String v = (String) request.getValue();
        int c = v.length() * 2;
        if (len > -1)
            c -= len;
        value = v;
        serializedData = null;
        change(internals, c);
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(Internals internals, MakeValue request) throws Exception {
        if (len > -1)
            return false;
        String v = (String) request.getValue();
        int c = v.length() * 2;
        if (len > -1)
            c -= len;
        value = v;
        serializedData = null;
        change(internals, c);
        return true;
    }

    /**
     * Returns the value held by this component.
     *
     * @param internals The actor's internals.
     * @return The value held by this component, or null.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    @Override
    protected String getValue(Internals internals) throws Exception {
        return getValue();
    }

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component, or null.
     */
    public String getValue() {
        if (len == -1)
            return null;
        if (value != null)
            return value;
        ReadableBytes readableBytes = serializedData.readable();
        skipLen(readableBytes);
        value = readableBytes.readString(len);
        return value;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        if (len == -1)
            saveLen(appendableBytes);
        else
            appendableBytes.writeString(value);
    }

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(String o) {
        return value.compareTo(o);
    }
}
