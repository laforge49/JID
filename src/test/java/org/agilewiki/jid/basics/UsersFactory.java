package org.agilewiki.jid.basics;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class UsersFactory extends ActorFactory {
    public UsersFactory(String actorType) {
        super(actorType);
    }

    @Override
    protected Users instantiateActor()
            throws Exception {
        Users users = new Users();
        users.valueFactory = StringJidFactory.fac;
        return users;
    }
}
