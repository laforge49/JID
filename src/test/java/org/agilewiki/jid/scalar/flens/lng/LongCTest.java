package org.agilewiki.jid.scalar.flens.lng;

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
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJidC;

public class LongCTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newLongJid = new NewJID(JidFactories.LONG_JID_CTYPE);
            Actor long1 = newLongJid.send(future, factory).thisActor();
            Actor long2 = (new CopyJID()).send(future, long1);
            (new SetLong(1L)).send(future, long2);
            Actor float3 = (new CopyJID()).send(future, long2);

            int sl = GetSerializedLength.req.send(future, long1);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, long2);
            assertEquals(8, sl);
            sl = GetSerializedLength.req.send(future, float3);
            assertEquals(8, sl);

            long v = GetLong.req.send(future, long1);
            assertEquals(0L, v);
            v = GetLong.req.send(future, long2);
            assertEquals(1L, v);
            v = GetLong.req.send(future, float3);
            assertEquals(1L, v);

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_CTYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvl = JidJidC.setValueReq(JidFactories.LONG_JID_CTYPE);
            sjvl.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = GetLong.req.send(future, rpa);
            assertEquals(0L, v);
            (new SetLong(-1000000000000L)).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = GetLong.req.send(future, rpa);
            assertEquals(-1000000000000L, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
