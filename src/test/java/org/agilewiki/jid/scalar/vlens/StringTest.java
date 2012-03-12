package org.agilewiki.jid.scalar.vlens;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.requests.CopyJID;
import org.agilewiki.jid.requests.GetSerializedLength;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJid;

public class StringTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewActor newStringJid = new NewActor(JidFactories.STRING_JID_TYPE);
            JCActor string1 = (JCActor) newStringJid.send(future, factory);
            Open.req.call(string1);
            Actor string2 = (new CopyJID()).send(future, string1);
            StringJid.setValueReq("abc").send(future, string2);
            Actor string3 = (new CopyJID()).send(future, string2);

            int sl = GetSerializedLength.req.send(future, string1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, string2);
            assertEquals(10, sl);
            sl = GetSerializedLength.req.send(future, string3);
            assertEquals(10, sl);

            assertNull(StringJid.getValueReq.send(future, string1));
            assertEquals("abc", StringJid.getValueReq.send(future, string2));
            assertEquals("abc", StringJid.getValueReq.send(future, string3));

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = (JCActor) newJidJid.send(future, factory);
            Open.req.call(jidJid1);
            SetValue sjvbs = JidJid.setValueReq(JidFactories.STRING_JID_TYPE);
            sjvbs.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertNull(StringJid.getValueReq.send(future, rpa));
            assertTrue(StringJid.makeValueReq("").send(future, rpa));
            assertFalse(StringJid.makeValueReq("Hello?").send(future, rpa));
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertEquals("", StringJid.getValueReq.send(future, rpa));
            StringJid.setValueReq("bye").send(future, rpa);
            assertEquals("bye", StringJid.getValueReq.send(future, rpa));
            sl = GetSerializedLength.req.send(future, rpa);
            assertEquals(10, sl);
            Clear.req.sendEvent(rpa);
            assertNull(StringJid.getValueReq.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
