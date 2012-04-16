package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeMapAppender extends Request<Long, MapAppender> {
    final static public TimeMapAppender req = new TimeMapAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof MapAppender;
    }
}
