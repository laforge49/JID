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

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.bind.JBActor;
import org.agilewiki.jactor.components.factory.NewActor;

/**
 * Creates a JID actor and loads its serialized data.
 */
final public class NewJID extends NewActor {
    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     */
    public NewJID(String actorType) {
        super(actorType);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, byte[] bytes) {
        this(actorType, null, null, bytes);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     */
    public NewJID(String actorType, Mailbox mailbox) {
        super(actorType, mailbox);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes) {
        this(actorType, mailbox, null, bytes);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     */
    public NewJID(String actorType, Mailbox mailbox, JBActor parent) {
        super(actorType, mailbox, parent);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, JBActor parent, byte[] bytes) {
        super(actorType, mailbox, parent);
        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    /**
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public byte[] getBytes() {
        return bytes;
    }
}
