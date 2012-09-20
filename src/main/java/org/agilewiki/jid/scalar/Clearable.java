package org.agilewiki.jid.scalar;

import org.agilewiki.jid._Jid;

public interface Clearable extends _Jid {

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    void clear() throws Exception;
}
