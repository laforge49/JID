package org.agilewiki.jid.collection.vlenc.map;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.scalar.vlens.string.StringJid;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class StringStringBMapJidTest extends TestCase {
    public void test() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());
            BMapJid<String, StringJid> m = (BMapJid) (new StringBMapJidFactory(
                    JidFactories.STRING_STRING_MAP_JID_TYPE, StringJidFactory.fac)).
                    newActor(factory.getMailbox(), factory);
            assertEquals(0, m.size());
            assertTrue(m.kMake("1"));
            assertFalse(m.kMake("1"));
            assertEquals(1, m.size());
            MapEntry<String, StringJid> me = m.iGet(0);
            assertEquals("1", me.getKey());
            m.empty();
            assertEquals(0, m.size());
            assertTrue(m.kMake("1"));
            assertEquals(1, m.size());
            me = m.iGet(0);
            assertEquals("1", me.getKey());
            m.iRemove(0);
            assertEquals(0, m.size());
            assertTrue(m.kMake("1"));
            assertEquals(1, m.size());
            me = m.iGet(0);
            assertEquals("1", me.getKey());
            assertFalse(m.kRemove("0"));
            assertTrue(m.kRemove("1"));
            assertEquals(0, m.size());
        } finally {
            mailboxFactory.close();
        }
    }
}
