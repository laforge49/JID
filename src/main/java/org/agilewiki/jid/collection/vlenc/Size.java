package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Returns the size of the collection.
 */
public class Size extends SynchronousRequest<Integer> {
    public final static Size req = new Size();
}
