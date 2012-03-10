package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Removes a JID from the ith position.
 * If i < 0, the new JID is removed from position size + 1 - i.
 */
public class IRemove extends SynchronousRequest<Object> {
    /**
     * The index of the desired element.
     */
    private int i;

    /**
     * Returns the index of the desired element.
     *
     * @return The index of the desired element.
     */
    public int getI() {
        return i;
    }

    /**
     * Create the request.
     *
     * @param i The index of the desired element.
     */
    public IRemove(int i) {
        this.i = i;
    }
}
