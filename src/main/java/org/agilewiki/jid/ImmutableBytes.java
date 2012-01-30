/*
 * Copyright 2011 Bill La Forge
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
package org.agilewiki.jid;

/**
 * <p>
 * An immutable wrapper for an array of bytes.
 * </p>
 */
final public class ImmutableBytes {
    /**
     * The wrapped immutable array of bytes.
     */
    private byte[] bytes;

    /**
     * An immutable offset into the array of bytes.
     */
    private int offset;

    /**
     * Create ImmutableBytes.
     *
     * @param bytes  The bytes to be wrapped.
     * @param offset An offset.
     */
    public ImmutableBytes(byte[] bytes, int offset) {
        this.bytes = bytes;
        this.offset = offset;
    }

    /**
     * Returns the wrapped bytes.
     * 
     * @return The wrapped bytes.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Returns the offset.
     * 
     * @return The offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Creates a MutableBytes object from this ImmutableBytes object.
     *
     * @return The MutableBytes object.
     */
    public MutableBytes mutable() {
        return new MutableBytes(bytes, offset);
    }

    /**
     * Checks that there are enough bytes after the offset.
     *
     * @param length The minimum number of bytes needed after the offset.
     */
    public void validate(int length) {
        if (offset + length > bytes.length)
            throw new IllegalStateException("not enough bytes remaining");
    }
}
