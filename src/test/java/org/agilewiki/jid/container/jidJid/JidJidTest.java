package org.agilewiki.jid.container.jidJid;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JidFactories;

public class JidJidTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);

            NewActor newJidJid = new NewActor(JidFactories.JID_JID_TYPE);
            JCActor jidJid1 = newJidJid.send(future, factory);
            JCActor jidJid2 = newJidJid.send(future, factory);
            JCActor jidJid3 = newJidJid.send(future, factory);

            MakeJIDValue mjvj = new MakeJIDValue(JidFactories.JID_TYPE);
            boolean made = mjvj.send(future, jidJid2);
            assertEquals(true, made);
            made = mjvj.send(future, jidJid2);
            assertEquals(false, made);
            MakeJIDValue mjvjj = new MakeJIDValue(JidFactories.JID_JID_TYPE);
            made = mjvjj.send(future, jidJid3);
            assertEquals(true, made);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
