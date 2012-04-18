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
package org.agilewiki.jid.jidFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.factory.GetActorFactory;
import org.agilewiki.jactor.factory.JAFactoryFactory;
import org.agilewiki.jactor.factory.NewActor;
import org.agilewiki.jactor.factory.Requirement;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid._Jid;

/**
 * An actor to create JID actors and load their serialized data.
 */
final public class JidsFactory extends JLPCActor {

    /**
     * Create a LiteActor
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public JidsFactory(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Returns the actor's requirements.
     *
     * @return The actor's requirents.
     */
    @Override
    protected Requirement[] requirements() throws Exception {
        Requirement[] requirements = new Requirement[1];
        requirements[0] = new Requirement(
                new NewActor(""),
                new JAFactoryFactory(JAFactoryFactory.TYPE));
        return requirements;
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        if (request instanceof GetJidFactory) {
            rp.processResponse(getJidFactory(((GetJidFactory) request).getActorType()));
            return;
        }
        if (request instanceof NewJID) {
            rp.processResponse(newJID((NewJID) request));
            return;
        }
    }

    protected JidFactory getJidFactory(String actorType)
            throws Exception {
        return (JidFactory) (new GetActorFactory(actorType)).call(this);
    }

    protected _Jid newJID(NewJID request)
            throws Exception {
        _Jid container = request.getContainer();
        ReadableBytes readableBytes = request.getReadableBytes();
        Actor actor = (new NewActor(
                request.getActorType(),
                request.getMailbox(),
                request.getParent())).call(this);
        _Jid jid = (_Jid) actor;
        if (readableBytes != null)
            jid.load(readableBytes);
        if (container != null)
            jid.setContainerJid(container);
        return jid;
    }
}
