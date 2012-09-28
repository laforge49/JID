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
import org.agilewiki.jid.Jid;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.UnionJid;
import org.agilewiki.jid.scalar.vlens.actor.UnionJidFactory;

/**
 * A balanced tree holding a list of JIDs, all of the same type.
 */
public class BListJid extends AppJid implements Collection, JAList {
    protected final int TUPLE_SIZE = 0;
    protected final int TUPLE_UNION = 1;
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

    protected void init()
            throws Exception {
        tupleFactories = new ActorFactory[2];
        tupleFactories[TUPLE_SIZE] = IntegerJidFactory.fac;
        tupleFactories[TUPLE_UNION] = new UnionJidFactory(null,
                new ListJidFactory("leaf", elementsFactory, nodeCapacity),
                new ListJidFactory("inode", new BListJidFactory(null, elementsFactory, nodeCapacity, false, false), nodeCapacity));
    }

    protected void setNodeType(String nodeType)
            throws Exception {
        getUnionJid().setValue(nodeType);
    }

    protected IntegerJid getSizeJid()
            throws Exception {
        return (IntegerJid) _iGet(TUPLE_SIZE);
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

    protected void incSize(int inc)
            throws Exception {
        IntegerJid sj = getSizeJid();
        sj.setValue(sj.getValue() + inc);
    }

    protected UnionJid getUnionJid()
            throws Exception {
        return (UnionJid) _iGet(TUPLE_UNION);
    }

    protected ListJid getNode()
            throws Exception {
        return (ListJid) getUnionJid().getValue();
    }

    protected boolean isLeaf()
            throws Exception {
        return getNode().getActorType().equals("leaf");
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
        if (n < 0 || n >= size())
            throw new IllegalArgumentException("pathname " + pathname);
        _Jid jid = iGet(n);
        if (s == pathname.length())
            return jid;
        return jid.resolvePathname(pathname.substring(s + 1));
    }

    @Override
    public void iAdd(int i)
            throws Exception {
        iAddBytes(i, null);
    }

    @Override
    public void iAddBytes(int i, byte[] bytes)
            throws Exception {
        if (i < 0)
            i = size() + 1 + i;
        ListJid node = getNode();
        if (isLeaf()) {
            if (bytes == null)
                node.iAdd(i);
            else
                node.iAddBytes(i, bytes);
            incSize(1);
            if (node.size() < nodeCapacity)
                return;
            if (isRoot) {
                rootLeafSplit();
                return;
            }
            inodeLeafSplit();
            return;
        }
        throw new UnsupportedOperationException("not leaf"); //todo
    }

    protected void rootLeafSplit()
            throws Exception {
        ListJid oldRoot = getNode();
        getUnionJid().setValue("inode");
        ListJid newRoot = getNode();
        newRoot.iAdd(0);
        newRoot.iAdd(1);
        BListJid left = (BListJid) newRoot.iGet(0);
        BListJid right = (BListJid) newRoot.iGet(1);
        left.setNodeType("leaf");
        right.setNodeType("leaf");
        int h = nodeCapacity / 2;
        int i = 0;
        while (i < h) {
            Jid e = (Jid) oldRoot.iGet(i);
            byte[] bytes = e.getSerializedBytes();
            left.iAddBytes(-1, bytes);
            i += 1;
        }
        while (i < nodeCapacity) {
            Jid e = (Jid) oldRoot.iGet(i);
            byte[] bytes = e.getSerializedBytes();
            right.iAddBytes(-1, bytes);
            i += 1;
        }
    }

    protected void inodeLeafSplit()
            throws Exception {
        throw new UnsupportedOperationException("inodeLeafSplit"); //todo
    }
}
