package org.agilewiki.jid.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.collection.flenc.TupleJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class UserFactory extends TupleJidFactory {
    public UserFactory(String actorType) {
        super(actorType, StringJidFactory.fac, StringJidFactory.fac, StringJidFactory.fac);
    }

    @Override
    protected User instantiateActor(Mailbox mailbox) throws Exception {
        User user = new User(mailbox);
        assignElementFactories(user);
        return user;
    }
}
