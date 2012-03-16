package org.agilewiki.jid.scalar.vlens.jidjid;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.*;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.Clear;
import org.agilewiki.jid.scalar.vlens.MakeValue;
import org.agilewiki.jid.scalar.vlens.string.GetString;
import org.agilewiki.jid.scalar.vlens.string.SetString;

public class JidJidCTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            int sl = GetSerializedLength.req.send(future, jidJid1);
            assertEquals(4, sl);
            Clear.req.send(future, jidJid1);
            sl = GetSerializedLength.req.send(future, jidJid1);
            assertEquals(4, sl);
            Actor jidJid1a = JidJidC.getValueReq.send(future, jidJid1);
            assertNull(jidJid1a);
            Actor rpa = (new ResolvePathname("")).send(future, jidJid1);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid1);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            assertNull(rpa);
            Actor jidJid11 = (new CopyJID()).send(future, jidJid1);
            assertNotNull(jidJid11);
            sl = GetSerializedLength.req.send(future, jidJid11);
            assertEquals(4, sl);
            rpa = (new ResolvePathname("")).send(future, jidJid11);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid11);
            rpa = (new ResolvePathname("0")).send(future, jidJid11);
            assertNull(rpa);

            NewJID newStringJid = new NewJID(JidFactories.STRING_JID_CTYPE);
            Actor string1 = newStringJid.send(future, factory).thisActor();
            (new SetString("abc")).send(future, string1);
            byte[] sb = GetBytes.req.send(future, string1);
            (new SetJidBytes(JidFactories.STRING_JID_CTYPE, sb)).send(future, jidJid1);
            Actor sj = JidJidC.getValueReq.send(future, jidJid1);
            assertEquals("abc", GetString.req.send(future, sj));

            Actor jidJid2 = newJidJid.send(future, factory).thisActor();
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(4, sl);
            SetValue sjvj = JidJidC.setValueReq(JidFactories.JID_CTYPE);
            sjvj.send(future, jidJid2);
            MakeValue<Jid, String, Actor> mjvj = JidJidC.makeValueReq(JidFactories.JID_CTYPE);
            boolean made = mjvj.send(future, jidJid2);
            assertEquals(false, made);
            Actor jidJid2a = JidJidC.getValueReq.send(future, jidJid2);
            assertNotNull(jidJid2a);
            sl = GetSerializedLength.req.send(future, jidJid2a);
            assertEquals(0, sl);
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(16, sl);
            rpa = (new ResolvePathname("")).send(future, jidJid2);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid2);
            rpa = (new ResolvePathname("0")).send(future, jidJid2);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid2a);
            Actor jidJid22 = (new CopyJID()).send(future, jidJid2);
            Clear.req.send(future, jidJid2);
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(4, sl);
            jidJid2a = JidJidC.getValueReq.send(future, jidJid2);
            assertNull(jidJid2a);
            assertNotNull(jidJid22);
            sl = GetSerializedLength.req.send(future, jidJid22);
            assertEquals(16, sl);
            rpa = (new ResolvePathname("")).send(future, jidJid22);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid22);
            rpa = (new ResolvePathname("0")).send(future, jidJid22);
            assertNotNull(rpa);
            sl = GetSerializedLength.req.send(future, rpa);
            assertEquals(0, sl);

            Actor jidJid3 = newJidJid.send(future, factory).thisActor();
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(4, sl);
            MakeValue<Jid, String, Actor> mjvjj = JidJidC.makeValueReq(JidFactories.JID_JID_CTYPE);
            made = mjvjj.send(future, jidJid3);
            assertEquals(true, made);
            made = mjvjj.send(future, jidJid3);
            assertEquals(false, made);
            Actor jidJid3a = JidJidC.getValueReq.send(future, jidJid3);
            assertNotNull(jidJid3a);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(28, sl);
            made = mjvj.send(future, jidJid3a);
            assertEquals(true, made);
            made = mjvj.send(future, jidJid3a);
            assertEquals(false, made);
            Actor jidJid3b = JidJidC.getValueReq.send(future, jidJid3a);
            assertNotNull(jidJid3b);
            sl = GetSerializedLength.req.send(future, jidJid3b);
            assertEquals(0, sl);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(16, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(40, sl);
            rpa = (new ResolvePathname("")).send(future, jidJid3);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid3);
            rpa = (new ResolvePathname("0")).send(future, jidJid3);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid3a);
            rpa = (new ResolvePathname("0/0")).send(future, jidJid3);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid3b);
            Actor jidJid33 = (new CopyJID()).send(future, jidJid3);
            Clear.req.send(future, jidJid3a);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(28, sl);
            jidJid3b = JidJidC.getValueReq.send(future, jidJid3a);
            assertNull(jidJid2a);
            Actor jidJid3aa = JidJidC.getValueReq.send(future, jidJid3);
            assertEquals(jidJid3a, jidJid3aa);
            assertNotNull(jidJid33);
            sl = GetSerializedLength.req.send(future, jidJid33);
            assertEquals(40, sl);
            rpa = (new ResolvePathname("")).send(future, jidJid33);
            assertNotNull(rpa);
            assertEquals(rpa, jidJid33);
            rpa = (new ResolvePathname("0")).send(future, jidJid33);
            assertNotNull(rpa);
            sl = GetSerializedLength.req.send(future, rpa);
            assertEquals(16, sl);
            rpa = (new ResolvePathname("0/0")).send(future, jidJid33);
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
