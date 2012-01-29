package org.agilewiki.jid;

/**
 * An immutable wrapper for an array of bytes. 
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
