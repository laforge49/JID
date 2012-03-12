package org.agilewiki.jid.scalar.flens;

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
import org.agilewiki.jid.requests.CopyJID;
import org.agilewiki.jid.requests.GetSerializedLength;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJid;

public class FloatTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewJID newFloatJid = new NewJID(JidFactories.FLOAT_JID_TYPE);
            Actor float1 = newFloatJid.send(future, factory).thisActor();
            Actor float2 = (new CopyJID()).send(future, float1);
            FloatJid.setValueReq(1.0f).send(future, float2);
            Actor float3 = (new CopyJID()).send(future, float2);

            int sl = GetSerializedLength.req.send(future, float1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, float2);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, float3);
            assertEquals(4, sl);

            float v = FloatJid.getValueReq.send(future, float1);
            assertEquals(0.f, v);
            v = FloatJid.getValueReq.send(future, float2);
            assertEquals(1.f, v);
            v = FloatJid.getValueReq.send(future, float3);
            assertEquals(1.f, v);

            NewJID newJidJid = new NewJID(JidFactories.JID_JID_TYPE);
            Actor jidJid1 = newJidJid.send(future, factory).thisActor();
            SetValue sjvf = JidJid.setValueReq(JidFactories.FLOAT_JID_TYPE);
            sjvf.send(future, jidJid1);
            Actor rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = FloatJid.getValueReq.send(future, rpa);
            assertEquals(0.f, v);
            FloatJid.setValueReq(-1.f).send(future, rpa);
            rpa = (new ResolvePathname("0")).send(future, jidJid1);
            v = FloatJid.getValueReq.send(future, rpa);
            assertEquals(-1.f, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
