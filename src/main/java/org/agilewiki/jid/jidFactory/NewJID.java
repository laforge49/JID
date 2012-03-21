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
import org.agilewiki.jactor.bind.JBConcurrentRequest;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.ReadableBytes;

/**
 * Creates a JID actor and loads its serialized data.
 */
final public class NewJID extends JBConcurrentRequest<Jid> {
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
    private Actor parent;

    /**
     * Holds the serialized data.
     */
    private ReadableBytes readableBytes;

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
        this(actorType, null, null, (byte[]) null, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, byte[] bytes) {
        this(actorType, null, null, bytes, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param bytes     Holds the serialized data.
     * @param container The container of the new Jid.
     */
    public NewJID(String actorType, byte[] bytes, Jid container) {
        this(actorType, null, null, bytes, container);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     */
    public NewJID(String actorType, Mailbox mailbox) {
        this(actorType, mailbox, null, (byte[]) null, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes) {
        this(actorType, mailbox, null, bytes, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param bytes     Holds the serialized data.
     * @param container The container of the new Jid.
     */
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes, Jid container) {
        this(actorType, mailbox, null, bytes, container);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     */
    public NewJID(String actorType, Mailbox mailbox, Actor parent) {
        this(actorType, mailbox, parent, (byte[]) null, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     * @param bytes     Holds the serialized data.
     */
    public NewJID(String actorType, Mailbox mailbox, Actor parent, byte[] bytes) {
        this(actorType, mailbox, parent, bytes, null);
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType An actor type name.
     * @param mailbox   A mailbox which may be shared with other actors, or null.
     * @param parent    The parent actor to which unrecognized requests are forwarded, or null.
     * @param bytes     Holds the serialized data.
     * @param container The container of the new Jid.
     */
    public NewJID(String actorType, Mailbox mailbox, Actor parent, byte[] bytes, Jid container) {
        this.actorType = actorType;
        this.mailbox = mailbox;
        this.parent = parent;
        if (bytes != null) {
            byte[] bs = new byte[bytes.length];
            System.arraycopy(bytes, 0, bs, 0, bytes.length);
            readableBytes = new ReadableBytes(bs, 0);
        }
        this.container = container;
    }

    /**
     * Create a NewJID request.
     *
     * @param actorType     An actor type name.
     * @param mailbox       A mailbox which may be shared with other actors, or null.
     * @param parent        The parent actor to which unrecognized requests are forwarded, or null.
     * @param readableBytes Holds the serialized data.
     * @param container     The container of the new Jid.
     */
    public NewJID(String actorType, Mailbox mailbox, Actor parent, ReadableBytes readableBytes, Jid container) {
        this.actorType = actorType;
        this.mailbox = mailbox;
        this.parent = parent;
        this.readableBytes = readableBytes;
        this.container = container;
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
    public Actor getParent() {
        return parent;
    }

    /**
     * Returns the serialized data.
     *
     * @return The serialized data.
     */
    public ReadableBytes getReadableBytes() {
        return readableBytes;
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
