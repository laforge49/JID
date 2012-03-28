package org.agilewiki.jid.collection.vlenc.map.string;

import org.agilewiki.jid.jidFactory.JidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

/**
 * Holds a map with String keys and StringJidA values.
 */
final public class StringStringJAMapJidC extends StringMapJidC {
    /**
     * Returns the JidFactory for the values in the map.
     *
     * @return The JidFactory for the values in the list.
     */
    protected JidFactory getValueFactory()
            throws Exception {
        return new StringJidFactory();
    }
}
