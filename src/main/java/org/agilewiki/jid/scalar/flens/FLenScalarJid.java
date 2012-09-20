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
package org.agilewiki.jid.scalar.flens;

import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.scalar.ScalarJid;

/**
 * A JID actor that holds a fixed-length value.
 * The value is always present.
 */
abstract public class FLenScalarJid<RESPONSE_TYPE extends Comparable>
        extends ScalarJid<RESPONSE_TYPE, RESPONSE_TYPE>
        implements ComparableKey<RESPONSE_TYPE> {

    /**
     * The value.
     */
    protected RESPONSE_TYPE value = newValue();

    /**
     * Create the value.
     *
     * @return The default value
     */
    abstract protected RESPONSE_TYPE newValue();

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes)
            throws Exception {
        super.load(readableBytes);
        readableBytes.skip(getSerializedLength());
        value = null;
    }

    /**
     * Assign a value.
     *
     * @param v The new value.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void setValue(RESPONSE_TYPE v) throws Exception {
        if (v.equals(value))
            return;
        value = v;
        serializedBytes = null;
        serializedOffset = -1;
        change(0);
    }

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(RESPONSE_TYPE o) throws Exception {
        return getValue().compareTo(o);
    }
}
