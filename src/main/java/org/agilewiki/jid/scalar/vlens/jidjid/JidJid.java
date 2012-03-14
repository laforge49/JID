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
package org.agilewiki.jid.scalar.vlens.jidjid;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.*;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.GetValue;
import org.agilewiki.jid.scalar.SetValue;
import org.agilewiki.jid.scalar.vlens.MakeValue;
import org.agilewiki.jid.scalar.vlens.VLenScalarJid;

/**
 * A JID component that holds a JID actor.
 */
public class JidJid extends VLenScalarJid<Jid, String, Actor>
        implements ComparableKey<Object> {
    /**
     * The GetValue request.
     */
    public static final GetValue<String, Actor> getValueReq = (GetValue<String, Actor>) GetValue.req;

    /**
     * Returns the MakeValue request.
     *
     * @param actorType The actor type.
     * @return The MakeValue request.
     */
    public static final MakeValue makeValueReq(String actorType) {
        return new MakeValue(actorType);
    }

    /**
     * Returns the SetValue request.
     *
     * @param actorType The actor type.
     * @return The SetValue request.
     */
    public static final SetValue setValueReq(String actorType) {
        return new SetValue(actorType);
    }

    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(SetBytes.class.getName(), new VoidSynchronousMethodBinding<SetBytes>() {
            @Override
            public void synchronousProcessRequest(Internals internals, SetBytes request)
                    throws Exception {
                setBytes(request);
            }
        });

        thisActor.bind(MakeBytes.class.getName(), new SynchronousMethodBinding<MakeBytes, Boolean>() {
            @Override
            public Boolean synchronousProcessRequest(Internals internals, MakeBytes request)
                    throws Exception {
                return makeBytes(internals, request);
            }
        });
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
     * @param request The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(MakeValue request)
            throws Exception {
        if (len > -1)
            return false;
        String jidType = (String) request.getValue();
        setValue(jidType);
        return true;
    }

    /**
     * Assign a value.
     *
     * @param jidType The actor type.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void setValue(String jidType)
            throws Exception {
        NewJID na = new NewJID(jidType, thisActor.getMailbox(), thisActor.getParent(), (byte[]) null, this);
        value = na.call(thisActor);
        int l = Util.stringLength(jidType) + value.getSerializedLength();
        change(l);
        serializedBytes = null;
        serializedOffset = -1;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param request The SetBytes request.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setBytes(SetBytes request)
            throws Exception {
        if (len > -1)
            clear();
        String jidType = request.getActorType();
        byte[] bytes = request.getBytes();
        setBytes(jidType, bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected Boolean makeBytes(Internals internals, MakeBytes request)
            throws Exception {
        if (len > -1)
            return false;
        String jidType = request.getActorType();
        byte[] bytes = request.getBytes();
        setBytes(jidType, bytes);
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
        NewJID na = new NewJID(jidType, thisActor.getMailbox(), thisActor.getParent(), bytes, this);
        value = na.call(thisActor);
        int l = Util.stringLength(jidType) + value.getSerializedLength();
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
    protected Actor getValue()
            throws Exception {
        Jid v = get();
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
    private Jid get()
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
                thisActor.getMailbox(),
                thisActor.getParent(), readableBytes, this)).call(thisActor);
        return value;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
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
            return thisActor;
        }
        if (pathname.equals("0")) {
            return getValue();
        }
        if (pathname.startsWith("0/")) {
            Jid v = get();
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
    public int compareKeyTo(Object o) {
        ComparableKey<Object> v = (ComparableKey<Object>) value;
        return v.compareKeyTo(o);
    }
}
