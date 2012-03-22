package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.bind.JBSynchronousRequest;

/**
 * Create a keyed entry.
 */
public class KMake<KEY_TYPE> extends JBSynchronousRequest<Boolean> {
    /**
     * The key.
     */
    private KEY_TYPE key;

    /**
     * Returns the key to be used.
     *
     * @return The key to be used.
     */
    public KEY_TYPE getKey() {
        return key;
    }

    /**
     * Create the request.
     *
     * @param key The key.
     */
    public KMake(KEY_TYPE key) {
        this.key = key;
    }
}
