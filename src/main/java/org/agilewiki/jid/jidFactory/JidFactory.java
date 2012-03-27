/*
 * Copyright 2011 Bill La Forge
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
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.ActorFactory;
import org.agilewiki.jactor.components.factory.JCActorFactory;
import org.agilewiki.jactor.components.factory.JLPCActorFactory;
import org.agilewiki.jid.*;

/**
 * Create a Jid actor.
 */
public class JidFactory {
    /**
     * The actor factory.
     */
    private JCActorFactory jcActorFactory;

    /**
     * The actor factory.
     */
    private JLPCActorFactory jlpcActorFactory;

    /**
     * Create a JidFactory.
     *
     * @param actorFactory The actor factory.
     */
    public JidFactory(JCActorFactory actorFactory) {
        this.jcActorFactory = actorFactory;
    }

    /**
     * Create a JidFactory.
     *
     * @param actorFactory The actor factory.
     */
    public JidFactory(JLPCActorFactory actorFactory) {
        this.jlpcActorFactory = actorFactory;
    }

    /**
     * Create a JidFactory.
     *
     * @param actorFactory The actor factory.
     */
    public JidFactory(ActorFactory actorFactory) {
        if (actorFactory instanceof JCActorFactory)
            this.jcActorFactory = (JCActorFactory) actorFactory;
        else
            this.jlpcActorFactory = (JLPCActorFactory) actorFactory;
    }

    public Jid newJID(Mailbox mailbox, Actor parent, Jid container, ReadableBytes readableBytes)
            throws Exception {
        if (jcActorFactory != null) {
            JCActor jcActor = jcActorFactory.newActor(mailbox, parent);
            JidC jidC = GetJIDComponent.req.call(jcActor);
            if (readableBytes != null)
                jidC.load(readableBytes);
            if (container != null)
                jidC.setContainerJid(container);
            Open.req.call(jcActor);
            return jidC;
        }
        JidA jidA = (JidA) jlpcActorFactory.newActor(mailbox, parent);
        if (readableBytes != null)
            jidA.load(readableBytes);
        if (container != null)
            jidA.setContainerJid(container);
        return jidA;
    }

    public Actor newActor(Mailbox mailbox, Actor parent, Jid container, ReadableBytes readableBytes)
            throws Exception {
        return newJID(mailbox, parent, container, readableBytes).thisActor();
    }
}
