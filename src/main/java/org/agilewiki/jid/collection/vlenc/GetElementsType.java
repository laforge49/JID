package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.ConcurrentRequest;

/**
 * Returns the actor type of the elements.
 */
public class GetElementsType extends ConcurrentRequest<String> {
    public final static GetElementsType req = new GetElementsType();
}
