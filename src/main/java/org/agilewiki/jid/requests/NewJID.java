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
package org.agilewiki.jid.requests;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.components.factory.NewActor;

/**
 * Creates a JID actor and loads its serialized data.
 */
public class NewJID extends NewActor {
    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, byte[] bytes) {
        this(actorType, null, null, null, bytes, 0, bytes.length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param bytes     Holds the serialized data.
     * @param offset    The position of the serialized data.
     * @param length    The length of the serialized data.
     */
    public NewJID(String actorType, byte[] bytes, int offset, int length) {
        this(actorType, null, null, null, bytes, offset, length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes) {
        this(actorType, mailbox, null, null, bytes, 0, bytes.length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param bytes     Holds the serialized data.
     * @param offset    The position of the serialized data.
     * @param length    The length of the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes, int offset, int length) {
        this(actorType, mailbox, null, null, bytes, offset, length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param actorName The name to be assigned to the actor, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, String actorName, byte[] bytes) {
        this(actorType, mailbox, actorName, null, bytes, 0, bytes.length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param actorName The name to be assigned to the actor, or null.
     * @param bytes     Holds the serialized data.
     * @param offset    The position of the serialized data.
     * @param length    The length of the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, String actorName, byte[] bytes, int offset, int length) {
        this(actorType, mailbox, actorName, null, bytes, offset, length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param actorName The name to be assigned to the actor, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, String actorName, Actor parent, byte[] bytes) {
        this(actorType, mailbox, actorName, parent, bytes, 0, bytes.length);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param actorName The name to be assigned to the actor, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     * @param bytes     Holds the serialized data.
     * @param offset    The position of the serialized data.
     * @param length    The length of the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, String actorName, Actor parent, byte[] bytes, int offset, int length) {
        super(actorType, mailbox, actorName, parent);
        this.bytes = new byte[length];
        System.arraycopy(bytes, offset, this.bytes, 0, length);
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
