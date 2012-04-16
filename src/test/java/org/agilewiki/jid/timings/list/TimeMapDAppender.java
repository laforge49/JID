package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

public class TimeMapDAppender extends Request<Long, MapDAppender> {
    final static public TimeMapDAppender req = new TimeMapDAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof MapDAppender;
    }
}
