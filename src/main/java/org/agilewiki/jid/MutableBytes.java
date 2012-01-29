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
 *     A mutable wrapper for an array of bytes.
 * </p>
 * <p>
 *     Reads and writes read from and write to the wrapped byte array
 *     while advancing an internal offset.
 * </p>
 */
final public class MutableBytes {
    /**
     * The wrapped mutable array of bytes.
     */
    private byte[] bytes;

    /**
     * A mutable offset into the array of bytes.
     */
    private int offset;

    /**
     * Create MutableBytes.
     * 
     * @param bytes  The bytes to be wrapped.
     * @param offset An offset.
     */
    public MutableBytes(byte[] bytes, int offset) {
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
     * Creates an ImmutableBytes object from a mutableBytes object.
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
     * Checks that there are enough bytes after the offset.
     * 
     * @param length The minimum number of bytes needed after the offset.
     */
    public void validate(int length) {
        if (offset + length > bytes.length)
            throw new IllegalStateException("not enough bytes remaining");
    }

    /**
     * Advance the offset.
     * 
     * @param length The number of bytes to be skipped over. 
     */
    public void skip(int length) {
        validate(length);
        offset += length;
    }

    /**
     * Write selected bytes.
     *
     * @param immutableBytes The source of the bytes to be written.
     * @param length         The number of bytes to be written.
     */
    public void writeImmutableBytes(ImmutableBytes immutableBytes, int length) {
        System.arraycopy(immutableBytes.getBytes(), immutableBytes.getOffset(), bytes, offset, length);
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
     * @param ba The array containing the bytes to be written.
     * @param off The offset to the bytes to be written.
     * @param len The number of bytes to be written.
     */
    public void writeBytes(byte[] ba, int off, int len) {
        if (len == 0) return;
        System.arraycopy(ba, off, bytes, offset, len);
        offset += len;
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
     * @param ba The array of bytes to be read into.
     * @param off The offset into the array of bytes to be read into.
     * @param len The number of bytes to be read.
     */
    public void readBytes(byte[] ba, int off, int len) {
        System.arraycopy(bytes, offset, ba, off, len);
        offset += len;
    }

    /**
     * Write an int.
     * 
     * @param i  The int to be written.
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
     * Read an int.
     *
     * @return The it that was read.
     */
    public int readInt() {
        int w = (int) readByte();
        w = (w << 8) | (int) readByte();
        w = (w << 8) | (int) readByte();
        return (w << 8) | (int) readByte();
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
     * Read a long.
     *
     * @return The long that was read.
     */
    public long readLong() {
        long w = (long) readByte();
        w = (w << 8) | (long) readByte();
        w = (w << 8) | (long) readByte();
        w = (w << 8) | (long) readByte();
        w = (w << 8) | (long) readByte();
        w = (w << 8) | (long) readByte();
        w = (w << 8) | (long) readByte();
        return (w << 8) | (long) readByte();
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
            char c = ca[i];
            writeByte((byte) (255 & (c >> 8)));
            writeByte((byte) (255 & c));
            i += 1;
        }
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
        char[] ca = new char[l];
        int i = 0;
        while (i < l) {
            ca[l] = (char) ((readByte() << 8) | readByte());
            i += 1;
        }
        return new String(ca);
    }

    /**
     * Read string.
     *
     * @return The string that was read.
     */
    public String readString() {
        return readString(readInt());
    }
}
