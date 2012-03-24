package org.agilewiki.jid.collection.vlenc.map.string;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.NewJID;

public class StringStringJAMapJidCTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);
            JAFuture future = new JAFuture();
            NewJID newMapJid = new NewJID(JidFactories.STRING_STRINGJA_MAP_JID_CTYPE);
            Actor m = newMapJid.send(future, factory).thisActor();
            assertNull(StringMapJidC.newKGet("a").send(future, m));
            assertTrue(StringMapJidC.newKMake("b").send(future, m));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
