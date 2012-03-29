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
package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.*;
import org.agilewiki.jid.jidFactory.JidFactory;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.vlens.VLenScalarJid;

/**
 * A JID actor that holds a JID actor.
 */
public class ActorJid
        extends VLenScalarJid<_Jid, String, Actor>
        implements ComparableKey<Object> {
    /**
     * Create an actor jid.
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public ActorJid(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp)
            throws Exception {
        if (request instanceof GetActor)
            rp.processResponse(getValue());
        else if (request instanceof SetActor) {
            SetActor setActor = (SetActor) request;
            String actorType = setActor.getValue();
            if (actorType != null)
                setValue(actorType);
            else
                setValue(setActor.getJidFactory());
            rp.processResponse(null);
        } else if (request instanceof MakeActor) {
            MakeActor makeActor = (MakeActor) request;
            String actorType = makeActor.getValue();
            if (actorType != null)
                rp.processResponse(makeValue(actorType));
            else
                rp.processResponse(makeValue(makeActor.getJidFactory()));
        } else if (request instanceof SetActorBytes) {
            SetActorBytes setActorBytes = (SetActorBytes) request;
            String actorType = setActorBytes.getActorType();
            if (actorType != null)
                setJidBytes(actorType, setActorBytes.getBytes());
            else
                setJidBytes(setActorBytes.getJidFactory(), setActorBytes.getBytes());
            rp.processResponse(null);
        } else if (request instanceof MakeActorBytes) {
            MakeActorBytes makeActorBytes = (MakeActorBytes) request;
            String actorType = makeActorBytes.getActorType();
            if (actorType != null)
                rp.processResponse(makeJidBytes(actorType, makeActorBytes.getBytes()));
            else
                rp.processResponse(makeJidBytes(makeActorBytes.getJidFactory(), makeActorBytes.getBytes()));
        } else super.processRequest(request, rp);
    }

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void clear() throws Exception {
        if (len == -1)
            return;
        int l = len;
        if (value != null) {
            value.setContainerJid(null);
            value = null;
        }
        serializedBytes = null;
        serializedOffset = -1;
        change(-l);
        len = -1;
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param jidType The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(String jidType)
            throws Exception {
        if (len > -1)
            return false;
        setValue(jidType);
        return true;
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param jidFactory The actor type.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected Boolean makeValue(JidFactory jidFactory)
            throws Exception {
        if (len > -1)
            return false;
        setValue(jidFactory);
        return true;
    }

    /**
     * Assign a value.
     *
     * @param jidType The actor type.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void setValue(String jidType)
            throws Exception {
        NewJID na = new NewJID(jidType, getMailbox(), getParent(), (byte[]) null, this);
        value = na.call(this);
        int l = Util.stringLength(jidType) + value.getSerializedLength();
        change(l);
        serializedBytes = null;
        serializedOffset = -1;
    }

    /**
     * Assign a value.
     *
     * @param jidFactory The actor type.
     * @throws Exception Any uncaught exception raised.
     */
    public void setValue(JidFactory jidFactory)
            throws Exception {
        value = jidFactory.newJID(getMailbox(), thisActor().getParent(), this);
        int l = Util.stringLength(jidFactory.getActorType()) + value.getSerializedLength();
        change(l);
        serializedBytes = null;
        serializedOffset = -1;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setJidBytes(String actorType, byte[] bytes)
            throws Exception {
        if (len > -1)
            clear();
        setBytes(actorType, bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected Boolean makeJidBytes(String actorType, byte[] bytes)
            throws Exception {
        if (len > -1)
            return false;
        setBytes(actorType, bytes);
        return true;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param jidType The actor type.
     * @param bytes   The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setBytes(String jidType, byte[] bytes)
            throws Exception {
        NewJID na = new NewJID(jidType, getMailbox(), getParent(), bytes, this);
        value = na.call(this);
        int l = Util.stringLength(jidType) + value.getSerializedLength();
        change(l);
        serializedBytes = null;
        serializedOffset = -1;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setJidBytes(JidFactory jidFactory, byte[] bytes)
            throws Exception {
        if (len > -1)
            clear();
        setBytes(jidFactory, bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected Boolean makeJidBytes(JidFactory jidFactory, byte[] bytes)
            throws Exception {
        if (len > -1)
            return false;
        setBytes(jidFactory, bytes);
        return true;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setBytes(JidFactory jidFactory, byte[] bytes)
            throws Exception {
        value = jidFactory.newJID(getMailbox(), thisActor().getParent(), this, bytes);
        int l = Util.stringLength(jidFactory.getActorType()) + value.getSerializedLength();
        change(l);
        serializedBytes = null;
        serializedOffset = -1;
    }

    /**
     * Returns the actor held by this component.
     *
     * @return The actor held by this component, or null.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    public Actor getValue()
            throws Exception {
        _Jid v = get();
        if (v == null)
            return null;
        return value.thisActor();
    }

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component, or null.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    private _Jid get()
            throws Exception {
        if (len == -1)
            return null;
        if (value != null)
            return value;
        if (len == -1) {
            return null;
        }
        ReadableBytes readableBytes = readable();
        skipLen(readableBytes);
        String actorType = readableBytes.readString();
        value = (new NewJID(
                actorType,
                getMailbox(),
                getParent(), readableBytes, this)).call(this);
        return value;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
        saveLen(appendableBytes);
        if (len == -1)
            return;
        String actorType = value.getActorType();
        appendableBytes.writeString(actorType);
        value.save(appendableBytes);
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public Actor resolvePathname(String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return this;
        }
        if (pathname.equals("0")) {
            return getValue();
        }
        if (pathname.startsWith("0/")) {
            _Jid v = get();
            if (v == null)
                return null;
            return v.resolvePathname(pathname.substring(2));
        }
        throw new IllegalArgumentException("pathname " + pathname);
    }

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(Object o)
            throws Exception {
        ComparableKey<Object> v = (ComparableKey<Object>) value;
        return v.compareKeyTo(o);
    }
}
