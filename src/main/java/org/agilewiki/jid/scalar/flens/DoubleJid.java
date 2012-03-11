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

import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.scalar.GetValue;
import org.agilewiki.jid.scalar.SetValue;

/**
 * A JID component that holds a double.
 */
public class DoubleJid
        extends FLenScalarJid<Double> {
    /**
     * The GetValue request.
     */
    public static final GetValue<Double> getValueReq = (GetValue<Double>) GetValue.req;

    /**
     * Returns the SetValue request.
     *
     * @param value The value.
     * @return The SetValue request.
     */
    public static final SetValue setValueReq(Double value) {
        return new SetValue(value);
    }

    /**
     * Create the value.
     *
     * @return The default value
     */
    @Override
    protected Double newValue() {
        return new Double(0.D);
    }

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component.
     */
    public Double getValue() {
        if (value != null)
            return value;
        ReadableBytes readableBytes = readable();
        value = readableBytes.readDouble();
        return value;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.DOUBLE_LENGTH;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        appendableBytes.writeDouble(value);
    }
}
