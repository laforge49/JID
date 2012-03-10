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
package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.collection.CollectionJid;
import org.agilewiki.jid.requests.GetJIDComponent;

import java.util.ArrayList;

/**
 * Holds an ArrayList of JID actors, all of the same type.
 */
public class ListJid extends CollectionJid {
    /**
     * Actor type of the elements.
     */
    private String elementsType;

    /**
     * A list of JID actors.
     */
    private ArrayList<JID> list;

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    protected int size() {
        return list.size();
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    protected JID get(int i) {
        return list.get(i);
    }

    /**
     * Reset the collection.
     */
    protected void reset() {
        list = null;
    }

    /**
     * Load the elements type.
     *
     * @param internals The actor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    protected void loadElementsType(Internals internals)
            throws Exception {
        if (elementsType != null)
            return;
        elementsType = GetElementsType.req.call(internals, thisActor);
    }

    /**
     * Deserialize if serialized data is available.
     *
     * @param internals The actor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    protected void deserialize(Internals internals)
            throws Exception {
        if (list != null)
            return;
        list = new ArrayList<JID>();
        if (!isSerialized())
            return;
        loadElementsType(internals);
        ReadableBytes readableBytes = serializedData.readable();
        skipLen(readableBytes);
        int limit = len + readableBytes.getOffset();
        while (readableBytes.getOffset() < limit) {
            NewActor newActor = new NewActor(
                    elementsType,
                    thisActor.getMailbox(),
                    thisActor.getParent());
            JCActor elementActor = newActor.call(thisActor.getParent());
            JID elementJid = GetJIDComponent.req.call(internals, elementActor);
            elementJid.load(readableBytes);
            Open.req.call(internals, elementActor);
            len += elementJid.getSerializedLength();
            elementJid.containerJid = this;
            list.add(elementJid);
        }
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
        deserialize(internals);
        return super.resolvePathname(internals, pathname);
    }
}