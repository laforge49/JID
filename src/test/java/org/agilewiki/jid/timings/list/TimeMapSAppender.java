package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeMapSAppender extends Request<Long, MapSAppender> {
    final static public TimeMapSAppender req = new TimeMapSAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof MapSAppender;
    }
}
