package org.agilewiki.jid.container.jidJid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.container.Clear;
import org.agilewiki.jid.requests.GetSerializedLength;

public class JidJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            int sl = GetSerializedLength.req.send(future, jidJid1);
            assertEquals(4, sl);
            JCActor jidJid2 = newJidJid.send(future, factory);
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(4, sl);
            JCActor jidJid3 = newJidJid.send(future, factory);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(4, sl);

            Clear.req.send(future, jidJid1);
            sl = GetSerializedLength.req.send(future, jidJid1);
            assertEquals(4, sl);

            MakeJIDValue mjvj = new MakeJIDValue(JidFactories.JID_TYPE);
            boolean made = mjvj.send(future, jidJid2);
            assertEquals(true, made);
            made = mjvj.send(future, jidJid2);
            assertEquals(false, made);
            JCActor jidJid2a = GetJIDValue.req.send(future, jidJid2);
            assertNotNull(jidJid2a);
            sl = GetSerializedLength.req.send(future, jidJid2a);
            assertEquals(0, sl);
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(14, sl);
            Clear.req.send(future, jidJid2);
            sl = GetSerializedLength.req.send(future, jidJid2);
            assertEquals(4, sl);

            MakeJIDValue mjvjj = new MakeJIDValue(JidFactories.JID_JID_TYPE);
            made = mjvjj.send(future, jidJid3);
            assertEquals(true, made);
            made = mjvjj.send(future, jidJid3);
            assertEquals(false, made);
            JCActor jidJid3a = GetJIDValue.req.send(future, jidJid3);
            assertNotNull(jidJid3a);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(24, sl);
            made = mjvj.send(future, jidJid3a);
            assertEquals(true, made);
            made = mjvj.send(future, jidJid3a);
            assertEquals(false, made);
            JCActor jidJid3b = GetJIDValue.req.send(future, jidJid3a);
            assertNotNull(jidJid3b);
            sl = GetSerializedLength.req.send(future, jidJid3b);
            assertEquals(0, sl);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(14, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(34, sl);
            Clear.req.send(future, jidJid3a);
            sl = GetSerializedLength.req.send(future, jidJid3a);
            assertEquals(4, sl);
            sl = GetSerializedLength.req.send(future, jidJid3);
            assertEquals(24, sl);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
