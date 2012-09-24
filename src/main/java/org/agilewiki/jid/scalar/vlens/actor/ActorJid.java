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

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.*;
import org.agilewiki.jid.scalar.vlens.VLenScalarJid;

/**
 * A JID actor that holds a JID actor.
 */
public class ActorJid
        extends VLenScalarJid<String, Jid> implements Reference {
    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void clear() throws Exception {
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
    public Boolean makeValue(String jidType)
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
    @Override
    public Boolean makeValue(JidFactory jidFactory)
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
        value = createSubordinate(jidType);
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
    @Override
    public void setValue(ActorFactory jidFactory)
            throws Exception {
        value = createSubordinate(jidFactory);
        int l = Util.stringLength(jidFactory.actorType) + value.getSerializedLength();
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
    @Override
    public void setJidBytes(String actorType, byte[] bytes)
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
    @Override
    public Boolean makeJidBytes(String actorType, byte[] bytes)
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
    public void setBytes(String jidType, byte[] bytes)
            throws Exception {
        value = createSubordinate(jidType, bytes);
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
    @Override
    public void setJidBytes(ActorFactory jidFactory, byte[] bytes)
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
    @Override
    public Boolean makeJidBytes(JidFactory jidFactory, byte[] bytes)
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
    public void setBytes(ActorFactory jidFactory, byte[] bytes)
            throws Exception {
        value = createSubordinate(jidFactory, bytes);
        int l = Util.stringLength(jidFactory.actorType) + value.getSerializedLength();
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
    @Override
    public Jid getValue()
            throws Exception {
        if (len == -1)
            return null;
        if (value != null)
            return (Jid) value;
        if (len == -1) {
            return null;
        }
        ReadableBytes readableBytes = readable();
        skipLen(readableBytes);
        String actorType = readableBytes.readString();
        value = createSubordinate(actorType, readableBytes);
        return (Jid) value;
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
    public _Jid resolvePathname(String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return this;
        }
        if (pathname.equals("0")) {
            return getValue();
        }
        if (pathname.startsWith("0/")) {
            _Jid v = getValue();
            if (v == null)
                return null;
            return v.resolvePathname(pathname.substring(2));
        }
        throw new IllegalArgumentException("pathname " + pathname);
    }
}
