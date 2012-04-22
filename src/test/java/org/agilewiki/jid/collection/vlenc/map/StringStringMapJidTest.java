package org.agilewiki.jid.collection.vlenc.map;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.vlens.string.StringJid;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class StringStringMapJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Actor factory = new JidFactories(mailboxFactory.createMailbox());
            factory.setParent(null);
            JAFuture future = new JAFuture();
            Actor m = (new StringMapJidFactory(JidFactories.STRING_STRING_MAP_JID_TYPE, StringJidFactory.fac)).
                    newActor(factory.getMailbox(), factory);
            assertNull(StringMapJid.newKGet("a").send(future, m));
            assertTrue(StringMapJid.newKMake("b").send(future, m));
            assertNull(StringMapJid.newKGet("a").send(future, m));
            Actor value = StringMapJid.newKGet("b").send(future, m);
            assertNotNull(value);
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
