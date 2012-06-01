package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class Proc extends Request<Object, Main> {
    public final static Proc req = new Proc();

    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        Main a = (Main) targetActor;
        a.processRequest(this, rp);
    }

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof Main;
    }
}
