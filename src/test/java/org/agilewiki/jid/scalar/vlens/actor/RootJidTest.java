package org.agilewiki.jid.scalar.vlens.actor;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.*;
import org.agilewiki.jid.scalar.vlens.Clear;
import org.agilewiki.jid.scalar.vlens.string.GetString;
import org.agilewiki.jid.scalar.vlens.string.SetString;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class RootJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            Actor factory = new JidFactories(mailboxFactory.createMailbox());
            factory.setParent(null);

            RootJidFactory rootJidFactory = new RootJidFactory();
            Actor rootJid1 = rootJidFactory.newActor(factory.getMailbox(), factory);
            int sl = GetSerializedLength.req.send(future, rootJid1);
            assertEquals(0, sl);
            Clear.req.send(future, rootJid1);
            sl = GetSerializedLength.req.send(future, rootJid1);
            assertEquals(0, sl);
            Actor rootJid1a = GetActor.req.send(future, rootJid1);
            assertNull(rootJid1a);
            Actor rpa = (new ResolvePathname("")).send(future, rootJid1);
            assertNotNull(rpa);
            assertEquals(rpa, rootJid1);
            rpa = (new ResolvePathname("0")).send(future, rootJid1);
            assertNull(rpa);
            Actor rootJid11 = (new CopyJID()).send(future, rootJid1);
            assertNotNull(rootJid11);
            sl = GetSerializedLength.req.send(future, rootJid11);
            assertEquals(0, sl);
            rpa = (new ResolvePathname("")).send(future, rootJid11);
            assertNotNull(rpa);
            assertEquals(rpa, rootJid11);
            rpa = (new ResolvePathname("0")).send(future, rootJid11);
            assertNull(rpa);

            StringJidFactory stringJidAFactory = StringJidFactory.fac;
            Actor string1 = stringJidAFactory.newActor(factory.getMailbox(), factory);
            (new SetString("abc")).send(future, string1);
            byte[] sb = GetSerializedBytes.req.send(future, string1);
            (new SetActorBytes(stringJidAFactory, sb)).send(future, rootJid1);
            Actor sj = GetActor.req.send(future, rootJid1);
            assertEquals("abc", GetString.req.send(future, sj));

            Actor rootJid2 = RootJidFactory.fac.newActor(factory.getMailbox(), factory);
            sl = GetSerializedLength.req.send(future, rootJid2);
            assertEquals(0, sl);
            SetActor sjvj = new SetActor(JidFactories.JID_TYPE);
            sjvj.send(future, rootJid2);
            MakeActor mjvj = new MakeActor(JidFactories.JID_TYPE);
            boolean made = mjvj.send(future, rootJid2);
            assertEquals(false, made);
            Actor jidJid2a = GetActor.req.send(future, rootJid2);
            assertNotNull(jidJid2a);
            sl = GetSerializedLength.req.send(future, jidJid2a);
            assertEquals(0, sl);
            sl = GetSerializedLength.req.send(future, rootJid2);
            assertEquals(10, sl);
            rpa = (new ResolvePathname("")).send(future, rootJid2);
            assertNotNull(rpa);
            assertEquals(rpa, rootJid2);
            rpa = (new ResolvePathname("0")).send(future, rootJid2);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid2a);
            Actor rootJid22 = (new CopyJID()).send(future, rootJid2);
            Clear.req.send(future, rootJid2);
            sl = GetSerializedLength.req.send(future, rootJid2);
            assertEquals(0, sl);
            jidJid2a = GetActor.req.send(future, rootJid2);
            assertNull(jidJid2a);
            assertNotNull(rootJid22);
            sl = GetSerializedLength.req.send(future, rootJid22);
            assertEquals(10, sl);
            rpa = (new ResolvePathname("")).send(future, rootJid22);
            assertNotNull(rpa);
            assertEquals(rpa, rootJid22);
            rpa = (new ResolvePathname("0")).send(future, rootJid22);
            assertNotNull(rpa);
            sl = GetSerializedLength.req.send(future, rpa);
            assertEquals(0, sl);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
