package org.agilewiki.jid.scalar.flens.flt;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedLength;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.SetActor;

public class FloatTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JidFactories factory = new JidFactories();
            factory.initialize(mailboxFactory.createMailbox());

            FloatJid float1 = (FloatJid) FloatJidFactory.fac.newActor(factory.getMailbox(), factory);
            FloatJid float2 = (FloatJid) (new CopyJID()).send(future, float1);
            (new SetFloat(1.0f)).send(future, float2);
            Actor float3 = (new CopyJID()).send(future, float2);

            int sl = GetSerializedLength.req.send(future, float1);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, float2);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, float3);
            assertEquals(4, sl);

            float v = GetFloat.req.send(future, float1);
            assertEquals(0.f, v);
            v = GetFloat.req.send(future, float2);
            assertEquals(1.f, v);
            v = GetFloat.req.send(future, float3);
            assertEquals(1.f, v);

            Actor jidJid1 = ActorJidFactory.fac.newActor(factory.getMailbox(), factory);
            SetActor sjvf = new SetActor(JidFactories.FLOAT_JID_TYPE);
            sjvf.send(future, jidJid1);
            FloatJid rpa = (FloatJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetFloat.req.send(future, rpa);
            assertEquals(0.f, v);
            (new SetFloat(-1.0f)).send(future, rpa);
            rpa = (FloatJid) (new ResolvePathname("0")).send(future, jidJid1);
            v = GetFloat.req.send(future, rpa);
            assertEquals(-1.f, v);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
