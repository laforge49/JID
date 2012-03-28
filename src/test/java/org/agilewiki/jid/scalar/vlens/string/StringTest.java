package org.agilewiki.jid.scalar.vlens.string;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.Clear;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class StringTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newStringJid = new NewJID(JidFactories.STRING_JID_TYPE);
            StringJid string1 = (StringJid) newStringJid.send(future, factory).thisActor();
            StringJid string2 = (StringJid) (new CopyJID()).send(future, string1);
            (new SetString("abc")).send(future, string2);
            StringJid string3 = (StringJid) (new CopyJID()).send(future, string2);

            int sl = GetSerializedLength.req.send(future, string1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, string2);
            assertEquals(10, sl);
            sl = GetSerializedLength.req.send(future, string3);
            assertEquals(10, sl);

            assertNull(GetString.req.send(future, string1));
            assertEquals("abc", GetString.req.send(future, string2));
            assertEquals("abc", GetString.req.send(future, string3));

            Actor jidJid1 = (new ActorJidFactory()).newActor(factory.getMailbox(), factory);
            SetActor sjvbs = new SetActor(JidFactories.STRING_JID_TYPE);
            sjvbs.send(future, jidJid1);
            StringJid rpa = (StringJid) (new ResolvePathname("0")).send(future, jidJid1);
            assertNull(GetString.req.send(future, rpa));
            assertTrue((new MakeString("")).send(future, rpa));
            assertFalse((new MakeString("Hello?")).send(future, rpa));
            rpa = (StringJid) (new ResolvePathname("0")).send(future, jidJid1);
            assertEquals("", GetString.req.send(future, rpa));
            (new SetString("bye")).send(future, rpa);
            assertEquals("bye", GetString.req.send(future, rpa));
            sl = GetSerializedLength.req.send(future, rpa);
            assertEquals(10, sl);
            Clear.req.sendEvent(rpa);
            assertNull(GetString.req.send(future, rpa));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
