package org.agilewiki.jid.collection.vlenc.map.string;

import org.agilewiki.jid.collection.vlenc.map.KGet;

/**
 * Returns the value assigned to a given key.
 */
final public class StringKGet extends KGet<String> {
    /**
     * Create the request.
     *
     * @param key The key.
     */
    public StringKGet(String key) {
        super(key);
    }
}
