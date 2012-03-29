/*
 * Copyright 2012 Bill La Forge
 *
 * This file is part of AgileWiki and is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (LGPL) as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * or navigate to the following url http://www.gnu.org/licenses/lgpl-2.1.txt
 *
 * Note however that only Scala, Java and JavaScript files are being covered by LGPL.
 * All other files are covered by the Common Public License (CPL).
 * A copy of this license is also included and can be
 * found as well at http://www.opensource.org/licenses/cpl1.0.txt
 */
package org.agilewiki.jid.collection.flenc;

import junit.framework.TestCase;
import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.CopyJID;
import org.agilewiki.jid.GetSerializedBytes;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.ResolvePathname;
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.ISetBytes;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.string.GetString;
import org.agilewiki.jid.scalar.vlens.string.SetString;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

public class TupleTest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JAFuture future = new JAFuture();
            JCActor factory = new JCActor(mailboxFactory.createMailbox());
            (new Include(JidFactories.class)).call(factory);
            Open.req.call(factory);
            JLPCActor sstf = new StringStringTupleFactories(factory.getMailbox());
            sstf.setParent(factory);
            NewJID newTupleJid = new NewJID(JidFactories.TUPLE_JID_TYPE, sstf.getMailbox(), sstf);
            Actor t0 = newTupleJid.send(future, sstf).thisActor();
            IGet iget0 = new IGet(0);
            IGet iget1 = new IGet(1);
            Actor e0 = iget0.send(future, t0);
            assertNull(GetString.req.send(future, e0));
            Actor e1 = iget1.send(future, t0);
            assertNull(GetString.req.send(future, e1));
            (new SetString("Apples")).send(future, e0);
            assertEquals("Apples", GetString.req.send(future, e0));
            (new SetString("Oranges")).send(future, e1);
            assertEquals("Oranges", GetString.req.send(future, e1));
            Actor t1 = (new CopyJID()).send(future, t0);
            Actor f0 = (new ResolvePathname("0")).send(future, t1);
            assertEquals("Apples", GetString.req.send(future, f0));
            Actor f1 = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Oranges", GetString.req.send(future, f1));

            Actor string1 = (new StringJidFactory()).newActor(sstf.getMailbox(), sstf);
            (new SetString("Peaches")).send(future, string1);
            byte[] sb = GetSerializedBytes.req.send(future, string1);
            (new ISetBytes(1, sb)).send(future, t1);
            Actor f1b = (new ResolvePathname("1")).send(future, t1);
            assertEquals("Peaches", GetString.req.send(future, f1b));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
