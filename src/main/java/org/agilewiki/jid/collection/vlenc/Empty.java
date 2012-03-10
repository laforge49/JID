package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Empty a collection.
 */
public class Empty extends SynchronousRequest<Object> {
    public final static Empty req = new Empty();
}
