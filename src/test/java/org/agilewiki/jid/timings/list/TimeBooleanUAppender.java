package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeBooleanUAppender extends Request<Long, BooleanUAppender> {
    final static public TimeBooleanUAppender req = new TimeBooleanUAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof BooleanUAppender;
    }
}
