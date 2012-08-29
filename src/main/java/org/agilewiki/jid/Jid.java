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
package org.agilewiki.jid;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jactor.lpc.JLPCActor;

import java.util.Arrays;

/**
 * Base class for Incremental Deserialization Actors.
 */
public class Jid extends JLPCActor implements _Jid {
    /**
     * The JID actor which holds this actor.
     */
    private _Jid containerJid;

    /**
     * Holds the serialized data.
     */
    protected byte[] serializedBytes;

    /**
     * The start of the serialized data.
     */
    protected int serializedOffset;

    final public Jid createSubordinate(ActorFactory factory)
            throws Exception {
        return createSubordinate(factory, getParent());
    }

    final public Jid createSubordinate(String actorType)
            throws Exception {
        return createSubordinate(actorType, getParent());
    }

    final public Jid createSubordinate(ActorFactory factory, Actor parent)
            throws Exception {
        Jid jid = (Jid) factory.newActor(getMailbox(), parent);
        jid.setContainerJid(this);
        return jid;
    }

    final public Jid createSubordinate(String actorType, Actor parent)
            throws Exception {
        Jid jid = (Jid) JAFactory.newActor(this, actorType, getMailbox(), parent);
        jid.setContainerJid(this);
        return jid;
    }

    final public Jid createSubordinate(ActorFactory factory, byte[] bytes)
            throws Exception {
        return createSubordinate(factory, getParent(), bytes);
    }

    final public Jid createSubordinate(String actorType, byte[] bytes)
            throws Exception {
        return createSubordinate(actorType, getParent(), bytes);
    }

    final public Jid createSubordinate(ActorFactory factory, Actor parent, byte[] bytes)
            throws Exception {
        if (bytes == null)
            return createSubordinate(factory, parent);
        Jid jid = (Jid) factory.newActor(getMailbox(), parent);
        jid.load(new ReadableBytes(bytes, 0));
        jid.setContainerJid(this);
        return jid;
    }

    final public Jid createSubordinate(String actorType, Actor parent, byte[] bytes)
            throws Exception {
        if (bytes == null)
            return createSubordinate(actorType, parent);
        Jid jid = (Jid) JAFactory.newActor(this, actorType, getMailbox(), parent);
        jid.load(new ReadableBytes(bytes, 0));
        jid.setContainerJid(this);
        return jid;
    }

    final public Jid createSubordinate(ActorFactory factory, ReadableBytes readableBytes)
            throws Exception {
        return createSubordinate(factory, getParent(), readableBytes);
    }

    final public Jid createSubordinate(String actorType, ReadableBytes readableBytes)
            throws Exception {
        return createSubordinate(actorType, getParent(), readableBytes);
    }

    final public Jid createSubordinate(ActorFactory factory, Actor parent, ReadableBytes readableBytes)
            throws Exception {
        Jid jid = (Jid) factory.newActor(getMailbox(), parent);
        if (readableBytes != null)
            jid.load(readableBytes);
        jid.setContainerJid(this);
        return jid;
    }

    final public Jid createSubordinate(String actorType, Actor parent, ReadableBytes readableBytes)
            throws Exception {
        Jid jid = (Jid) JAFactory.newActor(this, actorType, getMailbox(), parent);
        if (readableBytes != null)
            jid.load(readableBytes);
        jid.setContainerJid(this);
        return jid;
    }

    /**
     * Returns a readable form of the serialized data.
     *
     * @return A ReadableBytes wrapper of the serialized data.
     */
    final protected ReadableBytes readable() {
        return new ReadableBytes(serializedBytes, serializedOffset);
    }

    /**
     * Notification that the persistent data has changed.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the notification.
     */
    protected void changed(int lengthChange)
            throws Exception {
        serializedBytes = null;
        serializedOffset = -1;
        if (containerJid == null)
            return;
        containerJid.change(lengthChange);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(int lengthChange) throws Exception {
        changed(lengthChange);
    }

    /**
     * Assign the container.
     *
     * @param containerJid The container, or null.
     */
    @Override
    public void setContainerJid(_Jid containerJid) {
        this.containerJid = containerJid;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() throws Exception {
        return 0;
    }

    /**
     * Returns true when the persistent data is already serialized.
     *
     * @return True when the persistent data is already serialized.
     */
    final protected boolean isSerialized() {
        return serializedBytes != null;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
    }

    /**
     * Saves the persistent data in a byte array.
     *
     * @param appendableBytes Holds the byte array and offset.
     */
    @Override
    public void save(AppendableBytes appendableBytes)
            throws Exception {
        if (isSerialized()) {
            byte[] bs = appendableBytes.getBytes();
            int off = appendableBytes.getOffset();
            appendableBytes.writeBytes(serializedBytes, serializedOffset, getSerializedLength());
            serializedBytes = bs;
            serializedOffset = off;
        } else {
            serializedBytes = appendableBytes.getBytes();
            serializedOffset = appendableBytes.getOffset();
            serialize(appendableBytes);
        }
        if (serializedOffset + getSerializedLength() != appendableBytes.getOffset()) {
            System.err.println("\n" + getClass().getName());
            System.err.println("" + serializedOffset +
                    " + " + getSerializedLength() + " != " + appendableBytes.getOffset());
            throw new IllegalStateException();
        }
    }

    /**
     * Returns a byte array holding the serialized persistent data.
     *
     * @return The byte array holding the serialized persistent data.
     */
    public final byte[] getSerializedBytes()
            throws Exception {
        byte[] bs = new byte[getSerializedLength()];
        AppendableBytes appendableBytes = new AppendableBytes(bs, 0);
        save(appendableBytes);
        return bs;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes)
            throws Exception {
        serializedBytes = readableBytes.getBytes();
        serializedOffset = readableBytes.getOffset();
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public _Jid resolvePathname(String pathname) throws Exception {
        if (pathname != "")
            throw new IllegalArgumentException("pathname " + pathname);
        return this;
    }

    /**
     * Returns a copy of the actor.
     *
     * @param m The mailbox.
     * @return a copy of the actor.
     */
    final public Actor copyJID(Mailbox m)
            throws Exception {
        Mailbox mb = m;
        if (mb == null)
            mb = getMailbox();
        Jid jid = (Jid) getFactory().newActor(mb, getParent());
        jid.load(new ReadableBytes(getSerializedBytes(), 0));
        return jid;
    }

    final public void isJidEqual(Actor actor, final RP rp)
            throws Exception {
        if (!(actor instanceof Jid)) {
            rp.processResponse(false);
            return;
        }
        final Jid jidA = (Jid) actor;
        send(jidA, GetSerializedLength.req, new RP<Integer>() {
            @Override
            public void processResponse(Integer response) throws Exception {
                if (response.intValue() != getSerializedLength()) {
                    rp.processResponse(false);
                    return;
                }
                send(jidA, GetSerializedBytes.req, new RP<byte[]>() {
                    @Override
                    public void processResponse(byte[] response) throws Exception {
                        boolean eq = Arrays.equals(response, getSerializedBytes());
                        rp.processResponse(eq);
                    }
                });
            }
        });
    }
}
