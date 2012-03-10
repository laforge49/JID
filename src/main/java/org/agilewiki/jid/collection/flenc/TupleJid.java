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
package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.CollectionJid;
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.ISetBytes;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.requests.GetJIDComponent;

/**
 * Holds a fixed-size array of JID actors of various types.
 */
public class TupleJid
        extends CollectionJid
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
                return get(ndx).thisActor;
            }
        });

        thisActor.bind(ISetBytes.class.getName(), new VoidSynchronousMethodBinding<ISetBytes>() {
            @Override
            public void synchronousProcessRequest(Internals internals, ISetBytes request)
                    throws Exception {
                iSetBytes(internals, request.getI(), request.getBytes());
            }
        });
    }

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
        tuple = new JID[size()];
        int i = 0;
        len = 0;
        while (i < size()) {
            JID elementJid = createJid(i, internals, readableBytes);
            len += elementJid.getSerializedLength();
            elementJid.containerJid = this;
            tuple[i] = elementJid;
            i += 1;
        }
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param internals The actor's internals.
     * @param i         The index of the desired element.
     * @param bytes     Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    public void iSetBytes(Internals internals, int i, byte[] bytes)
            throws Exception {
        String actorType = actorTypes[i];
        JCActor elementActor = (new NewJID(
                actorType,
                thisActor.getMailbox(),
                thisActor.getParent(),
                bytes)).call(internals, thisActor.getParent());
        JID elementJid = GetJIDComponent.req.call(internals, elementActor);
        Open.req.call(internals, elementActor);
        JID oldElementJid = get(i);
        oldElementJid.containerJid = null;
        tuple[i] = elementJid;
        elementJid.containerJid = this;
        change(internals, elementJid.getSerializedLength() - oldElementJid.getSerializedLength());
    }

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    protected int size() {
        return actorTypes.length;
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    protected JID get(int i) {
        return tuple[i];
    }

    /**
     * Reset the collection.
     */
    protected void reset() {
        tuple = null;
    }

    /**
     * Compares element 0
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o) using element 0.
     */
    public int compareKeyTo(Object o) {
        ComparableKey<Object> e0 = (ComparableKey<Object>) get(0);
        return e0.compareKeyTo(o);
    }
}
