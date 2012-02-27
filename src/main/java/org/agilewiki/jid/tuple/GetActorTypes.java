package org.agilewiki.jid.tuple;

import org.agilewiki.jactor.bind.ConcurrentRequest;

/**
 * Returns an array of actor types.
 */
public class GetActorTypes extends ConcurrentRequest<String[]> {
    public final static GetActorTypes req = new GetActorTypes();
}
