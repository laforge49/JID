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
package org.agilewiki.jid.tuple;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.*;
import org.agilewiki.jid.requests.GetJIDComponent;

/**
 * Holds an array of actors.
 */
public class TupleJid
        extends JID
        implements ComparableKey<Object> {
    /**
     * An array of actor types, one for each element in the tuple.
     */
    protected String[] actorTypes;

    /**
     * A tuple of actors.
     */
    protected JID[] tuple;

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(IGet.class.getName(), new SynchronousMethodBinding<IGet, JCActor>() {
            @Override
            public JCActor synchronousProcessRequest(Internals internals, IGet request)
                    throws Exception {
                int ndx = request.getI();
                return tuple[ndx].thisActor;
            }
        });
    }

    /**
     * The size of the serialized (exclusive of its length header).
     */
    protected int len;

    protected JID createJid(int i, Internals internals, ReadableBytes readableBytes)
            throws Exception {
        String actorType = actorTypes[i];
        NewActor newActor = new NewActor(
                actorType,
                thisActor.getMailbox(),
                thisActor.getParent());
        JCActor elementActor = newActor.call(thisActor.getParent());
        JID elementJid = GetJIDComponent.req.call(internals, elementActor);
        if (readableBytes != null) {
            elementJid.load(readableBytes);
        }
        Open.req.call(internals, elementActor);
        return elementJid;
    }

    /**
     * Opening is called when a Open initialization request is processed,
     * but before the actor is marked as active.
     *
     * @param internals The actor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    @Override
    public void opening(Internals internals) throws Exception {
        actorTypes = GetActorTypes.req.call(internals, thisActor.getParent());
        ReadableBytes readableBytes = null;
        if (isSerialized()) {
            readableBytes = serializedData.readable();
            skipLen(readableBytes);
        }
        tuple = new JID[actorTypes.length];
        int i = 0;
        len = 0;
        while (i < actorTypes.length) {
            JID elementJid = createJid(i, internals, readableBytes);
            len += elementJid.getSerializedLength();
            elementJid.containerJid = TupleJid.this;
            tuple[i] = elementJid;
            i += 1;
        }
    }

    /**
     * Skip over the length at the beginning of the serialized data.
     *
     * @param readableBytes Holds the serialized data.
     */
    protected void skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }

    /**
     * Returns the size of the serialized data (exclusive of its length header).
     *
     * @param readableBytes Holds the serialized data.
     * @return The size of the serialized data (exclusive of its length header).
     */
    protected int loadLen(ReadableBytes readableBytes) {
        return readableBytes.readInt();
    }

    /**
     * Writes the size of the serialized data (exclusive of its length header).
     *
     * @param appendableBytes The object written to.
     */
    protected void saveLen(AppendableBytes appendableBytes) {
        appendableBytes.writeInt(len);
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH + len;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        saveLen(appendableBytes);
        int i = 0;
        while (i < actorTypes.length) {
            tuple[i].save(appendableBytes);
            i += 1;
        }
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        len = loadLen(readableBytes);
        tuple = null;
        readableBytes.skip(len);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param internals    The actor's internals.
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(Internals internals, int lengthChange) throws Exception {
        len += lengthChange;
        super.change(internals, lengthChange);
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
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        int n = 0;
        try {
            n = Integer.parseInt(ns);
        } catch (Exception ex) {
            throw new IllegalArgumentException("pathname " + pathname);
        }
        if (n < 0 || n >= tuple.length)
            throw new IllegalArgumentException("pathname " + pathname);
        JID jid = tuple[n];
        if (s == pathname.length())
            return jid.thisActor;
        return jid.resolvePathname(internals, pathname.substring(s + 1));
    }

    /**
     * Compares element 0
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o) using element 0.
     */
    public int compareKeyTo(Object o) {
        ComparableKey<Object> e0 = (ComparableKey<Object>) tuple[0];
        return e0.compareKeyTo(o);
    }
}
