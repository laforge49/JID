package org.agilewiki.jid.scalar.flen;

import junit.framework.TestCase;
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
import org.agilewiki.jid.scalar.vlen.jidjid.JidJid;

public class FloatTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);

            NewActor newFloatJid = new NewActor(JidFactories.FLOAT_JID_TYPE);
            JCActor float1 = newFloatJid.send(future, factory);
            Open.req.call(float1);
            JCActor float2 = (new CopyJID()).send(future, float1);
            Open.req.call(float2);
            FloatJid.setValueReq(1.0f).send(future, float2);
            JCActor float3 = (new CopyJID()).send(future, float2);
            Open.req.call(float3);

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

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            Open.req.call(jidJid1);
            SetValue sjvf = JidJid.setValueReq(JidFactories.FLOAT_JID_TYPE);
            sjvf.send(future, jidJid1);
            JCActor rpa = (new ResolvePathname("$")).send(future, jidJid1);
            v = FloatJid.getValueReq.send(future, rpa);
            assertEquals(0.f, v);
            FloatJid.setValueReq(-1.f).send(future, rpa);
            rpa = (new ResolvePathname("$")).send(future, jidJid1);
            v = FloatJid.getValueReq.send(future, rpa);
            assertEquals(-1.f, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
