package org.agilewiki.jid.timings.list;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class TimeMapUAppender extends Request<Long, MapUAppender> {
    final static public TimeMapUAppender req = new TimeMapUAppender();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof MapUAppender;
    }

    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        rp.processResponse(((MapUAppender) targetActor).time());
    }
}
