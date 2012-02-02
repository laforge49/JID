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
 * A mutable wrapper for an array of bytes.
 * </p>
 * <p>
 * Reads and writes read from and write to the wrapped byte array
 * while advancing an internal offset.
 * </p>
 */
final public class AppendableBytes {
    /**
     * The wrapped immutable array of bytes.
     */
    protected byte[] bytes;

    /**
     * A mutable offset into the array of bytes.
     */
    protected int offset;

    /**
     * Create AppendableBytes.
     *
     * @param bytes  The bytes to be wrapped.
     * @param offset An offset.
     */
    public AppendableBytes(byte[] bytes, int offset) {
        this.bytes = bytes;
        this.offset = offset;
    }

    /**
     * Create AppendableBytes.
     *
     * @param size The size of the byte array.
     */
    public AppendableBytes(int size) {
        this(new byte[size], 0);
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
     * Creates an ImmutableBytes object from this mutableBytes object.
     *
     * @return The ImmutableBytes object.
     */
    public ImmutableBytes immutable() {
        return new ImmutableBytes(bytes, offset);
    }

    /**
     * Sets the offset to 0.
     */
    public void rewind() {
        throw new UnsupportedOperationException();
    }

    /**
     * Advance the offset.
     *
     * @param length The number of bytes to be skipped over.
     */
    public void skip(int length) {
        throw new UnsupportedOperationException();
    }

    /**
     * Write selected bytes.
     *
     * @param immutableBytes The source of the bytes to be written.
     * @param length         The number of bytes to be written.
     */
    public void writeImmutableBytes(ImmutableBytes immutableBytes, int length) {
        immutableBytes.readBytes(bytes, offset, length);
        offset += length;
    }

    /**
     * Write a byte.
     *
     * @param b The byte to be written.
     */
    public void writeByte(byte b) {
        bytes[offset] = b;
        offset += 1;
    }

    /**
     * Write a boolean.
     *
     * @param b The boolean to be written.
     */
    public void writeBoolean(boolean b) {
        if (b) writeByte((byte) 1);
        else writeByte((byte) 0);
    }

    /**
     * Write an array of bytes.
     *
     * @param ba The bytes to be written.
     */
    public void writeBytes(byte[] ba) {
        if (ba.length == 0) return;
        System.arraycopy(ba, 0, bytes, offset, ba.length);
        offset += ba.length;
    }

    /**
     * Write part of an array of bytes.
     *
     * @param ba  The array containing the bytes to be written.
     * @param off The offset to the bytes to be written.
     * @param len The number of bytes to be written.
     */
    public void writeBytes(byte[] ba, int off, int len) {
        if (len == 0) return;
        System.arraycopy(ba, off, bytes, offset, len);
        offset += len;
    }

    /**
     * Write an int.
     *
     * @param i The int to be written.
     */
    public void writeInt(int i) {
        bytes[offset + 3] = (byte) (i & 255);
        int w = i >> 8;
        bytes[offset + 2] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 1] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset] = (byte) (w & 255);
        offset += 4;
    }

    /**
     * Write a long.
     *
     * @param l The long to be written.
     */
    public void writeLong(long l) {
        bytes[offset + 7] = (byte) (l & 255);
        long w = l >> 8;
        bytes[offset + 6] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 5] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 4] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 3] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 2] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset + 1] = (byte) (w & 255);
        w = w >> 8;
        bytes[offset] = (byte) (w & 255);
        offset += 8;
    }

    /**
     * Write a char.
     *
     * @param c The char to be written.
     */
    public void writeChar(char c) {
        writeByte((byte) (255 & (c >> 8)));
        writeByte((byte) (255 & c));
    }

    /**
     * Write a string as an int and a char array.
     * (This approach uses more bytes than other approaches but is not so computationally intensive
     * while preserving the full range of character values.)
     *
     * @param s The string to be written, or null.
     */
    public void writeString(String s) {
        if (s == null) {
            writeInt(-1);
            return;
        }
        writeInt(s.length());
        if (s.length() == 0)
            return;
        char[] ca = s.toCharArray();
        int i = 0;
        while (i < ca.length) {
            writeChar(ca[i]);
            i += 1;
        }
    }

    /**
     * Write a float.
     *
     * @param f The float to be written.
     */
    public void writeFloat(float f) {
        writeInt(Float.floatToIntBits(f));
    }

    /**
     * Write a double.
     *
     * @param d The double to be written.
     */
    public void writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
    }
}
