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
package org.agilewiki.jid.jidsFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.lpc.ConcurrentRequest;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid._Jid;

/**
 * A request to create a JID actor and loads its serialized data.
 */
final public class NewJID extends ConcurrentRequest<_Jid, JidsFactory> {
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
    private byte[] bytes;

    /**
     * The container of the new Jid.
     */
    private _Jid container;

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
    public NewJID(String actorType, byte[] bytes, _Jid container) {
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
    public NewJID(String actorType, Mailbox mailbox, byte[] bytes, _Jid container) {
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
    public NewJID(String actorType, Mailbox mailbox, Actor parent, byte[] bytes, _Jid container) {
        this.actorType = actorType;
        this.mailbox = mailbox;
        this.parent = parent;
        if (bytes != null) {
            this.bytes = new byte[bytes.length];
            System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
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
    public NewJID(String actorType, Mailbox mailbox, Actor parent, ReadableBytes readableBytes, _Jid container) {
        this.actorType = actorType;
        this.mailbox = mailbox;
        this.parent = parent;
        if (readableBytes != null) {
            this.bytes = new byte[readableBytes.remaining()];
            System.arraycopy(readableBytes.getBytes(), readableBytes.getOffset(), this.bytes, 0, readableBytes.remaining());
        }
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
        if (bytes == null)
            return null;
        return new ReadableBytes(bytes, 0);
    }

    /**
     * Returns the container.
     *
     * @return The container.
     */
    public _Jid getContainer() {
        return container;
    }

    /**
     * Send a synchronous request.
     *
     * @param targetActor The target actor.
     * @return The response.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected _Jid _call(JidsFactory targetActor)
            throws Exception {
        return targetActor.newJID(this);
    }

    /**
     * Returns true when targetActor is an instanceof TARGET_TYPE
     *
     * @param targetActor The actor to be called.
     * @return True when targetActor is an instanceof TARGET_TYPE.
     */
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof JidsFactory;
    }
}
