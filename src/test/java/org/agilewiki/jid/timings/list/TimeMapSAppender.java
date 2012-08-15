package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class TimeMapSAppender extends Request<Long, MapSAppender> {
    final static public TimeMapSAppender req = new TimeMapSAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof MapSAppender;
    }

    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        rp.processResponse(((MapSAppender) targetActor).time());
    }
}
