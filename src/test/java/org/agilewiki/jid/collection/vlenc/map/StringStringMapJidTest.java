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
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());
            JAFuture future = new JAFuture();
            Actor m = (new StringMapJidFactory(JidFactories.STRING_STRING_MAP_JID_TYPE, StringJidFactory.fac)).
                    newActor(factory.getMailbox(), factory);
            assertNull(new KGet<String, StringJid>("a").send(future, m));
            assertTrue(new KMake<String, StringJid>("b").send(future, m));
            assertNull(new KGet<String, StringJid>("a").send(future, m));
            Actor value = new KGet<String, StringJid>("b").send(future, m);
            assertNotNull(value);
            assertTrue(value instanceof StringJid);
            value = new GetHigher<String, StringJid>("a").send(future, m);
            assertTrue(value instanceof StringJid);
            assertNull(new GetHigher<String, StringJid>("b").send(future, m));
            value = new GetCeiling<String, StringJid>("b").send(future, m);
            assertTrue(value instanceof StringJid);
            assertNull(new GetCeiling<String, StringJid>("c").send(future, m));
            assertNull(new KGet<String, StringJid>("c").send(future, m));
            assertTrue(new KRemove<String, StringJid>("b").send(future, m));
            assertFalse(new KRemove<String, StringJid>("b").send(future, m));
            assertNull(new KGet<String, StringJid>("b").send(future, m));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
