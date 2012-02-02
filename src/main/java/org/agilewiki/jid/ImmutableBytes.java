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
     * Read into an array of bytes.
     *
     * @param ba  The array of bytes to be read into.
     * @param off The offset into the array of bytes to be read into.
     * @param len The number of bytes to be read.
     */
    public void readBytes(byte[] ba, int off, int len) {
        System.arraycopy(bytes, offset, ba, off, len);
        offset += len;
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
     * Creates a ReadableBytes object from this ImmutableBytes object.
     *
     * @return The ReadableBytes object.
     */
    public ReadableBytes readable() {
        return new ReadableBytes(bytes, offset);
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
