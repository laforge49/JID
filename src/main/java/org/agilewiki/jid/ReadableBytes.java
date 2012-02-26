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
 * A readable wrapper for an array of bytes.
 * </p>
 * <p>
 * Reads from the wrapped byte array
 * while advancing an internal offset.
 * </p>
 */
final public class ReadableBytes {
    /**
     * The wrapped immutable array of bytes.
     */
    protected byte[] bytes;

    /**
     * A mutable offset into the array of bytes.
     */
    protected int offset;

    /**
     * Create ReadableBytes.
     *
     * @param bytes  The bytes to be wrapped.
     * @param offset An offset.
     */
    public ReadableBytes(byte[] bytes, int offset) {
        this.bytes = bytes;
        this.offset = offset;
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
     * Creates an ImmutableBytes object from this readableBytes object.
     *
     * @return The ImmutableBytes object.
     */
    public ImmutableBytes immutable() {
        return new ImmutableBytes(bytes, offset);
    }

    /**
     * Returns the number of bytes after the offset.
     *
     * @return The number of remaining bytes.
     */
    public int remaining() {
        return bytes.length - offset;
    }

    /**
     * Sets the offset to 0.
     */
    public void rewind() {
        offset = 0;
    }

    /**
     * Advance the offset.
     *
     * @param length The number of bytes to be skipped over.
     */
    public void skip(int length) {
        if (offset + length > bytes.length)
            throw new IllegalStateException("not enough bytes remaining");
        offset += length;
    }

    /**
     * Read a byte.
     *
     * @return The byte that was read.
     */
    public byte readByte() {
        byte rv = bytes[offset];
        offset += 1;
        return rv;
    }

    /**
     * Read a boolean.
     *
     * @return The boolean that was read.
     */
    public boolean readBoolean() {
        return readByte() != (byte) 0;
    }

    /**
     * Read an array of bytes.
     *
     * @param len The number of bytes to be read.
     * @return The array of bytes that was read.
     */
    public byte[] readBytes(int len) {
        byte[] ba = new byte[len];
        System.arraycopy(bytes, offset, ba, 0, len);
        offset += len;
        return ba;
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
     * Read an int.
     *
     * @return The it that was read.
     */
    public int readInt() {
        int w = 255 & (int) readByte();
        w = (w << 8) | (255 & (int) readByte());
        w = (w << 8) | (255 & (int) readByte());
        return (w << 8) | (255 & (int) readByte());
    }

    /**
     * Read a char.
     *
     * @return The char that was read.
     */
    public char readChar() {
        return (char) ((readByte() << 8) | readByte());
    }

    /**
     * Read a long.
     *
     * @return The long that was read.
     */
    public long readLong() {
        long w = 255 & (long) readByte();
        w = (w << 8) | (255 & (long) readByte());
        w = (w << 8) | (255 & (long) readByte());
        w = (w << 8) | (255 & (long) readByte());
        w = (w << 8) | (255 & (long) readByte());
        w = (w << 8) | (255 & (long) readByte());
        w = (w << 8) | (255 & (long) readByte());
        return (w << 8) | (255 & (long) readByte());
    }

    /**
     * Read string.
     *
     * @param l The size of the char array to be read.
     * @return The string that was read.
     */
    public String readString(int l) {
        if (l == -1)
            return null;
        if (l == 0)
            return "";
        if (l < -1)
            throw new IllegalArgumentException("invalid string length: " + l);
        char[] ca = new char[l];
        int i = 0;
        while (i < l) {
            ca[i] = readChar();
            i += 1;
        }
        return new String(ca);
    }

    /**
     * Read string.
     *
     * @return The string that was read, or null.
     */
    public String readString() {
        int l = readInt();
        return readString(l);
    }

    /**
     * Read a float.
     *
     * @return The float that was read.
     */
    public float readFloat() {
        int i = readInt();
        return Float.intBitsToFloat(i);
    }

    /**
     * Read a double.
     *
     * @return The double that was read.
     */
    public double readDouble() {
        long l = readLong();
        return Double.longBitsToDouble(l);
    }
}
