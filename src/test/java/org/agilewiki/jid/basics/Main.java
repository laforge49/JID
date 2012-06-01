package org.agilewiki.jid.basics;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.TargetActor;

public interface Main extends TargetActor {
    public void processRequest(Proc req, RP rp) throws Exception;
}
