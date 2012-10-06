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
public class BListJid<ENTRY_TYPE extends Jid>
        extends AppJid implements Collection<ENTRY_TYPE>, JAList {
    protected final int TUPLE_SIZE = 0;
    protected final int TUPLE_UNION = 1;
    protected int nodeCapacity = 28;
    protected boolean isRoot;
    protected ActorFactory entryFactory;

    /**
     * Returns the JidFactory for all the elements in the list.
     *
     * @return The JidFactory for of all the elements in the list.
     */
    protected ActorFactory getEntryFactory()
            throws Exception {
        if (entryFactory == null)
            throw new IllegalStateException("entryFactory uninitialized");
        return entryFactory;
    }

    protected void init()
            throws Exception {
        tupleFactories = new ActorFactory[2];
        tupleFactories[TUPLE_SIZE] = IntegerJidFactory.fac;
        tupleFactories[TUPLE_UNION] = new UnionJidFactory(
                null,
                new ListJidFactory("leaf", getEntryFactory(), nodeCapacity),
                new ListJidFactory(
                        "inode",
                        new BListJidFactory(
                                null,
                                getEntryFactory(),
                                nodeCapacity,
                                false,
                                false),
                        nodeCapacity));
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

    protected ListJid<ENTRY_TYPE> getNode()
            throws Exception {
        return (ListJid) getUnionJid().getValue();
    }

    public String getNodeType()
            throws Exception {
        return getNode().getActorType();
    }

    public boolean isLeaf()
            throws Exception {
        return getNodeType().equals("leaf");
    }

    public int nodeSize()
            throws Exception {
        return getNode().size();
    }

    public boolean isFat() throws Exception {
        return nodeSize() >= nodeCapacity;
    }

    /**
     * Returns the selected element.
     *
     * @param ndx Selects the element.
     * @return The ith JID component, or null if the index is out of range.
     */
    @Override
    public ENTRY_TYPE iGet(int ndx)
            throws Exception {
        ListJid<ENTRY_TYPE> node = getNode();
        if (isLeaf()) {
            return (ENTRY_TYPE) node.iGet(ndx);
        }
        if (ndx < 0)
            ndx += size();
        if (ndx < 0 || ndx >= size())
            return null;
        int i = 0;
        while (i < node.size()) {
            BListJid<ENTRY_TYPE> bnode = (BListJid) node.iGet(i);
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
        ListJid<ENTRY_TYPE> node = getNode();
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
            BListJid<ENTRY_TYPE> bnode = (BListJid) node.iGet(i);
            int bns = bnode.size();
            if (ndx < bns) {
                bnode.iSetBytes(ndx, bytes);
                return;
            }
            ndx -= bns;
            i += 1;
        }
        throw new IllegalArgumentException();
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
    public void iAddBytes(int ndx, byte[] bytes)
            throws Exception {
        if (ndx < 0)
            ndx = size() + 1 + ndx;
        if (ndx < 0 || ndx > size())
            throw new IllegalArgumentException();
        incSize(1);
        ListJid<ENTRY_TYPE> node = getNode();
        if (isLeaf()) {
            if (bytes == null)
                node.iAdd(ndx);
            else
                node.iAddBytes(ndx, bytes);
            if (node.size() < nodeCapacity)
                return;
            if (isRoot) {
                rootSplit();
                return;
            }
            return;
        }
        int i = 0;
        while (true) {
            BListJid<ENTRY_TYPE> bnode = (BListJid) node.iGet(i);
            int bns = bnode.size();
            i += 1;
            if (ndx < bns || i == node.size()) {
                bnode.iAddBytes(ndx, bytes);
                if (bnode.isFat()) {
                    node.iAdd(i - 1);
                    BListJid<ENTRY_TYPE> left = (BListJid) node.iGet(i - 1);
                    left.setNodeType(bnode.getNodeType());
                    bnode.inodeSplit(left);
                    if (node.size() < nodeCapacity)
                        return;
                    if (isRoot) {
                        rootSplit();
                        return;
                    }
                }
                return;
            }
            ndx -= bns;
        }
    }

    protected void rootSplit()
            throws Exception {
        ListJid<ENTRY_TYPE> oldRootNode = getNode();
        String oldType = oldRootNode.getActorType();
        getUnionJid().setValue("inode");
        ListJid<ENTRY_TYPE> newRootNode = getNode();
        newRootNode.iAdd(0);
        newRootNode.iAdd(1);
        BListJid<ENTRY_TYPE> leftBNode = (BListJid) newRootNode.iGet(0);
        BListJid<ENTRY_TYPE> rightBNode = (BListJid) newRootNode.iGet(1);
        leftBNode.setNodeType(oldType);
        rightBNode.setNodeType(oldType);
        int h = nodeCapacity / 2;
        int i = 0;
        if (oldType.equals("leaf")) {
            while (i < h) {
                Jid e = (Jid) oldRootNode.iGet(i);
                byte[] bytes = e.getSerializedBytes();
                leftBNode.iAddBytes(-1, bytes);
                i += 1;
            }
            while (i < nodeCapacity) {
                Jid e = (Jid) oldRootNode.iGet(i);
                byte[] bytes = e.getSerializedBytes();
                rightBNode.iAddBytes(-1, bytes);
                i += 1;
            }
        } else {
            while (i < h) {
                BListJid<ENTRY_TYPE> e = (BListJid) oldRootNode.iGet(i);
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                leftBNode.append(bytes, eSize);
                i += 1;
            }
            while (i < nodeCapacity) {
                BListJid<ENTRY_TYPE> e = (BListJid) oldRootNode.iGet(i);
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                rightBNode.append(bytes, eSize);
                i += 1;
            }
        }
    }

    protected void inodeSplit(BListJid<ENTRY_TYPE> leftBNode)
            throws Exception {
        ListJid<ENTRY_TYPE> node = getNode();
        int h = nodeCapacity / 2;
        int i = 0;
        if (isLeaf()) {
            while (i < h) {
                Jid e = (Jid) node.iGet(0);
                node.iRemove(0);
                byte[] bytes = e.getSerializedBytes();
                leftBNode.iAddBytes(-1, bytes);
                i += 1;
            }
            incSize(-h);
        } else {
            while (i < h) {
                BListJid<ENTRY_TYPE> e = (BListJid) node.iGet(0);
                node.iRemove(0);
                int eSize = e.size();
                incSize(-eSize);
                byte[] bytes = e.getSerializedBytes();
                leftBNode.append(bytes, eSize);
                i += 1;
            }
        }
    }

    @Override
    public void empty()
            throws Exception {
        ListJid<ENTRY_TYPE> node = getNode();
        node.empty();
        IntegerJid sj = getSizeJid();
        sj.setValue(0);
    }

    @Override
    public void iRemove(int ndx)
            throws Exception {
        int s = size();
        if (ndx < 0)
            ndx += s;
        if (ndx < 0 || ndx >= s)
            throw new IllegalArgumentException();
        ListJid<ENTRY_TYPE> node = getNode();
        if (isLeaf()) {
            node.iRemove(ndx);
            incSize(-1);
            return;
        }
        int i = 0;
        while (i < node.size()) {
            BListJid<ENTRY_TYPE> bnode = (BListJid) node.iGet(i);
            int bns = bnode.size();
            if (ndx < bns) {
                bnode.iRemove(ndx);
                incSize(-1);
                int bnodeSize = bnode.size();
                if (bnodeSize > nodeCapacity / 3)
                    return;
                if (bnodeSize == 0) {
                    node.iRemove(ndx);
                } else {
                    if (i > 0) {
                        BListJid<ENTRY_TYPE> leftBNode = (BListJid) node.iGet(i - 1);
                        if (leftBNode.nodeSize() + bnodeSize < nodeCapacity) {
                            bnode.append(leftBNode);
                            node.iRemove(i);
                        }
                    }
                    if (i + 1 < node.size()) {
                        BListJid<ENTRY_TYPE> rightBNode = (BListJid) node.iGet(i + 1);
                        if (bnodeSize + rightBNode.nodeSize() < nodeCapacity) {
                            rightBNode.append(bnode);
                            node.iRemove(i + 1);
                        }
                    }
                }
                if (node.size() == 1 && isRoot && !isLeaf()) {
                    bnode = (BListJid) node.iGet(0);
                    setNodeType(bnode.getNodeType());
                    IntegerJid sj = getSizeJid();
                    sj.setValue(0);
                    bnode.append(this);
                }
                return;
            }
            ndx -= bns;
            i += 1;
        }
        throw new IllegalArgumentException();
    }

    void append(BListJid<ENTRY_TYPE> leftNode)
            throws Exception {
        ListJid<ENTRY_TYPE> node = getNode();
        int i = 0;
        if (isLeaf()) {
            while (i < node.size()) {
                Jid e = (Jid) node.iGet(i);
                leftNode.append(e.getSerializedBytes(), 1);
                i += 1;
            }
        } else {
            while (i < node.size()) {
                BListJid<ENTRY_TYPE> e = (BListJid) node.iGet(i);
                leftNode.append(e.getSerializedBytes(), e.size());
                i += 1;
            }
        }
    }

    void append(byte[] bytes, int eSize)
            throws Exception {
        ListJid<ENTRY_TYPE> node = getNode();
        node.iAddBytes(-1, bytes);
        incSize(eSize);
    }
}
