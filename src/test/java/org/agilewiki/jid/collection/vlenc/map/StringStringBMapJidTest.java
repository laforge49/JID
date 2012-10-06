package org.agilewiki.jid.collection.vlenc.map;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class StringStringBMapJidTest extends TestCase {
    public void test() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());
            Actor m = (new StringBMapJidFactory(JidFactories.STRING_STRING_MAP_JID_TYPE, StringJidFactory.fac)).
                    newActor(factory.getMailbox(), factory);
        } finally {
            mailboxFactory.close();
        }
    }
}
