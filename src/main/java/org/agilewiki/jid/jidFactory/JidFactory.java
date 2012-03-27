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
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.ReadableBytes;

/**
 * Creates a Jid actor.
 */
abstract public class JidFactory {
    /**
     * Create and configure an actor.
     *
     * @param mailbox       The mailbox of the new actor.
     * @param parent        The parent of the new actor.
     * @param readableBytes Holds the serialized data.
     * @param container     The container of the new Jid.
     * @return The new actor.
     */
    abstract public Jid newJID(Mailbox mailbox, Actor parent, Jid container, ReadableBytes readableBytes)
            throws Exception;

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    public Actor newActor(Mailbox mailbox, Actor parent, Jid container, ReadableBytes readableBytes)
            throws Exception {
        return newJID(mailbox, parent, container, readableBytes).thisActor();
    }

    /**
     * Create and configure an actor.
     *
     * @param mailbox The mailbox of the new actor.
     * @param parent  The parent of the new actor.
     * @return The new actor.
     */
    final public Actor newActor(Mailbox mailbox, Actor parent) throws Exception {
        return newJID(mailbox, parent, null, null).thisActor();
    }
}
