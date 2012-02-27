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
package org.agilewiki.jid.scalar.vlen;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.requests.GetJIDComponent;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.GetValue;
import org.agilewiki.jid.scalar.MakeValue;
import org.agilewiki.jid.scalar.SetValue;

/**
 * A JID component that holds a JID actor.
 */
public class JidJid extends VLenScalarJid<JID, JCActor> {
    /**
     * The GetValue request.
     */
    public static final GetValue<JCActor> getValueReq = (GetValue<JCActor>) GetValue.req;

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

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void clear(Internals internals) throws Exception {
        if (len == -1)
            return;
        int l = len;
        if (value != null) {
            value.containerJid = null;
            value = null;
        }
        serializedData = null;
        dser = true;
        change(internals, -l);
        len = -1;
    }

    /**
     * Assign a value.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void setValue(Internals internals, SetValue request)
            throws Exception {
        if (len > -1)
            clear(internals);
        String jidType = (String) request.getValue();
        NewActor na = new NewActor(jidType, thisActor.getMailbox(), null, thisActor.getParent());
        JCActor nja = na.call(thisActor);
        value = (new GetJIDComponent()).call(internals, nja);
        value.containerJid = this;
        int l = Util.stringLength(jidType) + value.getSerializedLength();
        change(internals, l);
        dser = true;
        serializedData = null;
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(Internals internals, MakeValue request)
            throws Exception {
        if (len > -1)
            return false;
        String jidType = (String) request.getValue();
        NewActor na = new NewActor(jidType, thisActor.getMailbox(), null, thisActor.getParent());
        JCActor nja = na.call(thisActor);
        value = (new GetJIDComponent()).call(internals, nja);
        value.containerJid = this;
        int l = Util.stringLength(jidType) + value.getSerializedLength();
        change(internals, l);
        dser = true;
        serializedData = null;
        return true;
    }

    /**
     * Returns the value held by this component.
     *
     * @param internals The actor's internals.
     * @return The value held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    protected JCActor getValue(Internals internals)
            throws Exception {
        if (dser)
            if (len == -1)
                return null;
            else
                return value.thisActor;
        ReadableBytes readableBytes = serializedData.readable();
        skipLen(readableBytes);
        if (len == -1) {
            dser = true;
            return null;
        }
        String actorType = readableBytes.readString();
        JCActor nja = (new NewActor(
                actorType,
                thisActor.getMailbox(),
                null,
                thisActor.getParent())).call(thisActor);
        dser = true;
        value = (new GetJIDComponent()).call(internals, nja);
        value.load(readableBytes);
        value.containerJid = this;
        return nja;
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
        String actorType = value.thisActor.getActorType();
        appendableBytes.writeString(actorType);
        value.save(appendableBytes);
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param internals The actor's internals.
     * @param pathname  A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public JCActor resolvePathname(Internals internals, String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return thisActor;
        }
        if (pathname.equals("$")) {
            return getValue(internals);
        }
        if (pathname.startsWith("$/")) {
            JCActor jca = getValue(internals);
            ResolvePathname req = new ResolvePathname(pathname.substring(2));
            return req.call(internals, jca);
        }
        throw new IllegalArgumentException("pathname " + pathname + ";");
    }
}
