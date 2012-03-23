package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.bind.JBSynchronousRequest;

/**
 * Returns the value assigned to a given key.
 */
public class KGet<KEY_TYPE> extends JBSynchronousRequest<Actor> {
    /**
     * The key.
     */
    private KEY_TYPE key;

    /**
     * Returns the key.
     *
     * @return The key.
     */
    public KEY_TYPE getKey() {
        return key;
    }

    /**
     * Create the request.
     *
     * @param key The key.
     */
    public KGet(KEY_TYPE key) {
        this.key = key;
    }
}
