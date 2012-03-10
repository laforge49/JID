package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jid.JidFactories;

/**
 * Defines collection of Strings.
 */
public class StringElements extends ElementsType {
    private static final String at = JidFactories.STRING_JID_TYPE;

    /**
     * Returns an actor type.
     *
     * @return An actor type.
     */
    protected String at() {
        return at;
    }
}
