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
import org.agilewiki.jactor.bind.ConcurrentRequest;
import org.agilewiki.jactor.bind.JBActor;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.Jid;

/**
 * Creates a JID actor and loads its serialized data.
 */
final public class NewJID extends ConcurrentRequest<JCActor> {
    /**
     * An actor type name.
     */
    private String actorType;

    /**
     * A mailbox which may be shared with other actors, or null.
     */
    private Mailbox mailbox;

    /**
     * The parent actor to which unrecognized requests are forwarded, or null.
     */
    private JBActor parent;

    /**
     * Holds the serialized data.
     */
    private byte[] bytes;

    /**
     * The container of the new Jid.
     */
    private Jid container;

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     */
    public NewJID(String actorType) {
        this(actorType, null, null, null);
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
        this(actorType, mailbox, null, null);
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
        this(actorType, mailbox, parent, null);
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
        this.actorType = actorType;
        this.mailbox = mailbox;
        this.parent = parent;
        this.bytes = new byte[bytes.length];
        if (bytes != null)
            System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    /**
     * Returns an actor type name.
     *
     * @return An actor type name.
     */
    public String getActorType() {
        return actorType;
    }

    /**
     * Returns a mailbox which may be shared with other actors.
     *
     * @return A mailbox which may be shared with other actors, or null.
     */
    public Mailbox getMailbox() {
        return mailbox;
    }

    /**
     * Returns the parent actor to which unrecognized requests are forwarded.
     *
     * @return The parent actor to which unrecognized requests are forwarded, or null.
     */
    public JBActor getParent() {
        return parent;
    }

    /**
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Returns the container.
     *
     * @return The container.
     */
    public Jid getContainer() {
        return container;
    }
}
