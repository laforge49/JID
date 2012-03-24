package org.agilewiki.jid.collection.vlenc.map.string;

import org.agilewiki.jid.JidFactories;

/**
 * Holds a map with String keys and StringJidA values.
 */
final public class StringStringJAMapJidC extends StringMapJidC {
    /**
     * Returns the actor type of the values in the map.
     *
     * @return The actor type of the values in the list.
     */
    protected String getValuesType()
            throws Exception {
        return JidFactories.STRING_JID_ATYPE;
    }
}
