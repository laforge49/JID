package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeBooleanDAppender extends Request<Long, BooleanDAppender> {
    final static public TimeBooleanAppender req = new TimeBooleanAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof BooleanDAppender;
    }
}
