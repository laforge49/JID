package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Creates a JID, loads its bytes and inserts it in the ith position.
 * If i < 0, the new JID is placed at position size + 1 - i.
 */
public class IAddBytes extends SynchronousRequest<Object> {
    /**
     * The index of the desired element.
     */
    private int i;

    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * Returns the index of the desired element.
     *
     * @return The index of the desired element.
     */
    public int getI() {
        return i;
    }

    /**
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Create the request.
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     */
    public IAddBytes(int i, byte[] bytes) {
        this.i = i;
        this.bytes = bytes;
    }
}
