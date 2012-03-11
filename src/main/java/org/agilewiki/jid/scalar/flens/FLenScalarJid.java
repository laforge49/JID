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

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.scalar.ScalarJid;
import org.agilewiki.jid.scalar.SetValue;

/**
 * A JID component that holds a fixed-length value that is always present.
 */
abstract public class FLenScalarJid<RESPONSE_TYPE extends Comparable>
        extends ScalarJid<RESPONSE_TYPE>
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
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        value = null;
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
        RESPONSE_TYPE v = (RESPONSE_TYPE) request.getValue();
        if (v.equals(value))
            return;
        value = v;
        serializedBytes = null;
        serializedOffset = -1;
        change(internals, 0);
    }

    /**
     * Returns the value held by this component.
     *
     * @param internals The actor's internals.
     * @return The value held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    @Override
    protected RESPONSE_TYPE getValue(Internals internals) throws Exception {
        return getValue();
    }

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component.
     */
    abstract public RESPONSE_TYPE getValue();

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(RESPONSE_TYPE o) {
        return value.compareTo(o);
    }
}
