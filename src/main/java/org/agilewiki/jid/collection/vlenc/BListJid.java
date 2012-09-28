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

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.scalar.flens.bool.BooleanJid;
import org.agilewiki.jid.scalar.flens.bool.BooleanJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;

/**
 * A balanced tree holding a list of JIDs, all of the same type.
 */
public class BListJid extends AppJid implements Collection {
    protected final int SIZE = 0;
    protected final int IS_LEAF = 1;
    protected final int NODE = 2;
    protected int nodeCapacity = 28;
    protected boolean isRoot;
    protected ActorFactory elementsFactory;

    /**
     * Returns the JidFactory for all the elements in the list.
     *
     * @return The JidFactory for of all the elements in the list.
     */
    protected ActorFactory getListFactory()
            throws Exception {
        if (elementsFactory == null)
            throw new IllegalStateException("elementFactory uninitialized");
        return elementsFactory;
    }

    protected void setLeafNode(boolean isLeaf)
            throws Exception {
        ActorFactory nodeElementsFactory = null;
        if (isLeaf)
            nodeElementsFactory = elementsFactory;
        else
            nodeElementsFactory = getFactory();
        tupleFactories = new ActorFactory[3];
        tupleFactories[SIZE] = IntegerJidFactory.fac;
        tupleFactories[IS_LEAF] = BooleanJidFactory.fac;
        tupleFactories[NODE] = new ListJidFactory(null, nodeElementsFactory, nodeCapacity);
        setSize(0);
        getIsLeafJid().setValue(isLeaf);
    }

    protected IntegerJid getSizeJid()
            throws Exception {
        return (IntegerJid) _iGet(SIZE);
    }

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    @Override
    public int size()
            throws Exception {
        return getSizeJid().getValue();
    }

    protected void setSize(int s)
            throws Exception {
        getSizeJid().setValue(s);
    }

    protected BooleanJid getIsLeafJid()
            throws Exception {
        return (BooleanJid) _iGet(IS_LEAF);
    }

    protected boolean isLeaf()
            throws Exception {
        return getIsLeafJid().getValue();
    }

    protected ListJid getNode()
            throws Exception {
        return (ListJid) _iGet(NODE);
    }

    /**
     * Returns the selected element.
     *
     * @param ndx Selects the element.
     * @return The ith JID component, or null if the index is out of range.
     */
    @Override
    public _Jid iGet(int ndx)
            throws Exception {
        ListJid node = getNode();
        if (isLeaf()) {
            return node.iGet(ndx);
        }
        if (ndx < 0)
            ndx += size();
        if (ndx < 0 || ndx >= size())
            return null;
        int i = 0;
        while (i < node.size()) {
            BListJid bnode = (BListJid) node.iGet(i);
            int bns = bnode.size();
            if (ndx < bns) {
                return bnode.iGet(ndx);
            }
            ndx -= bns;
            i += 1;
        }
        return null;
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param ndx   The index of the desired element.
     * @param bytes Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    @Override
    public void iSetBytes(int ndx, byte[] bytes)
            throws Exception {
        ListJid node = getNode();
        if (isLeaf()) {
            node.iSetBytes(ndx, bytes);
            return;
        }
        if (ndx < 0)
            ndx += size();
        if (ndx < 0 || ndx >= size())
            throw new IllegalArgumentException();
        int i = 0;
        while (i < node.size()) {
            BListJid bnode = (BListJid) node.iGet(i);
            int bns = bnode.size();
            if (ndx < bns) {
                bnode.iSetBytes(ndx, bytes);
                return;
            }
            ndx -= bns;
            i += 1;
        }
    }
}
