package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeBooleanSAppender extends Request<Long, BooleanSAppender> {
    final static public TimeBooleanSAppender req = new TimeBooleanSAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof BooleanSAppender;
    }
}
