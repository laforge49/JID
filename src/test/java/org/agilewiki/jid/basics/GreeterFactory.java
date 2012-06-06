package org.agilewiki.jid.basics;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.lpc.JLPCActor;

public class GreeterFactory extends ActorFactory {
    private String greeting;

    public GreeterFactory(String actorType, String greeting) {
        super(actorType);
        this.greeting = greeting;
    }

    @Override
    protected JLPCActor instantiateActor() throws Exception {
        Greeter greeter = new Greeter();
        greeter.greeting = greeting;
        return greeter;
    }
}
