package org.agilewiki.jid.collection.vlenc.map.string;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

public class StringStringMapJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Actor factory = new JidFactories(mailboxFactory.createMailbox());
            factory.setParent(null);
            JAFuture future = new JAFuture();
            NewJID newMapJid = new NewJID(JidFactories.STRING_STRING_MAP_JID_TYPE, factory.getMailbox(), factory);
            Actor m = newMapJid.send(future, factory).thisActor();
            assertNull(StringMapJid.newKGet("a").send(future, m));
            assertTrue(StringMapJid.newKMake("b").send(future, m));
            assertNull(StringMapJid.newKGet("a").send(future, m));
            Actor value = StringMapJid.newKGet("b").send(future, m);
            assertTrue(value instanceof StringJid);
            value = StringMapJid.newGetHigher("a").send(future, m);
            assertTrue(value instanceof StringJid);
            assertNull(StringMapJid.newGetHigher("b").send(future, m));
            value = StringMapJid.newGetCeiling("b").send(future, m);
            assertTrue(value instanceof StringJid);
            assertNull(StringMapJid.newGetCeiling("c").send(future, m));
            assertNull(StringMapJid.newKGet("c").send(future, m));
            assertTrue(StringMapJid.newKRemove("b").send(future, m));
            assertFalse(StringMapJid.newKRemove("b").send(future, m));
            assertNull(StringMapJid.newKGet("b").send(future, m));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
