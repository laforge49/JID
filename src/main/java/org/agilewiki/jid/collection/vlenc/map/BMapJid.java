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
package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid._Jid;
import org.agilewiki.jid.collection.Collection;
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.collection.vlenc.JAList;
import org.agilewiki.jid.collection.vlenc.ListJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.UnionJid;

/**
 * A balanced tree that holds a map.
 */
abstract public class BMapJid<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE extends Jid>
        extends AppJid implements Collection<MapEntry<KEY_TYPE, VALUE_TYPE>>, JAList {
    protected final int TUPLE_SIZE = 0;
    protected final int TUPLE_UNION = 1;
    protected int nodeCapacity = 28;
    protected boolean isRoot;
    public ActorFactory valueFactory;

    abstract protected ActorFactory getUnionJidFactory()
            throws Exception;

    /**
     * Converts a string to a key.
     *
     * @param skey The string to be converted.
     * @return The key.
     */
    abstract protected KEY_TYPE stringToKey(String skey);

    protected ActorFactory getValueFactory()
            throws Exception {
        if (valueFactory == null)
            throw new IllegalStateException("valueFactory uninitialized");
        return valueFactory;
    }

    protected void init()
            throws Exception {
        tupleFactories = new ActorFactory[2];
        tupleFactories[TUPLE_SIZE] = IntegerJidFactory.fac;
        tupleFactories[TUPLE_UNION] = getUnionJidFactory();
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
    public MapEntry<KEY_TYPE, VALUE_TYPE> iGet(int ndx)
            throws Exception {
        ListJid node = getNode();
        if (isLeaf()) {
            return (MapEntry<KEY_TYPE, VALUE_TYPE>) node.iGet(ndx);
        }
        if (ndx < 0)
            ndx += size();
        if (ndx < 0 || ndx >= size())
            return null;
        int i = 0;
        while (i < node.size()) {
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i);
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
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i);
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
        ListJid node = getNode();
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
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i);
            int bns = bnode.size();
            i += 1;
            if (ndx < bns || i == node.size()) {
                bnode.iAddBytes(ndx, bytes);
                if (bnode.isFat()) {
                    node.iAdd(i - 1);
                    BMapJid<KEY_TYPE, VALUE_TYPE> left = (BMapJid) node.iGet(i - 1);
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
        ListJid oldRootNode = getNode();
        String oldType = oldRootNode.getActorType();
        getUnionJid().setValue("inode");
        ListJid newRootNode = getNode();
        newRootNode.iAdd(0);
        newRootNode.iAdd(1);
        BMapJid<KEY_TYPE, VALUE_TYPE> leftBNode = (BMapJid) newRootNode.iGet(0);
        BMapJid<KEY_TYPE, VALUE_TYPE> rightBNode = (BMapJid) newRootNode.iGet(1);
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
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) oldRootNode.iGet(i);
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                leftBNode.append(bytes, eSize);
                i += 1;
            }
            while (i < nodeCapacity) {
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) oldRootNode.iGet(i);
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                rightBNode.append(bytes, eSize);
                i += 1;
            }
        }
    }

    protected void inodeSplit(BMapJid<KEY_TYPE, VALUE_TYPE> leftBNode)
            throws Exception {
        ListJid node = getNode();
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
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) node.iGet(0);
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
        ListJid node = getNode();
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
        ListJid node = getNode();
        if (isLeaf()) {
            node.iRemove(ndx);
            incSize(-1);
            return;
        }
        int i = 0;
        while (i < node.size()) {
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i);
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
                        BMapJid<KEY_TYPE, VALUE_TYPE> leftBNode = (BMapJid) node.iGet(i - 1);
                        if (leftBNode.nodeSize() + bnodeSize < nodeCapacity) {
                            bnode.append(leftBNode);
                            node.iRemove(i);
                        }
                    }
                    if (i + 1 < node.size()) {
                        BMapJid<KEY_TYPE, VALUE_TYPE> rightBNode = (BMapJid) node.iGet(i + 1);
                        if (bnodeSize + rightBNode.nodeSize() < nodeCapacity) {
                            rightBNode.append(bnode);
                            node.iRemove(i + 1);
                        }
                    }
                }
                if (node.size() == 1 && isRoot && !isLeaf()) {
                    bnode = (BMapJid) node.iGet(0);
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

    void append(BMapJid<KEY_TYPE, VALUE_TYPE> leftNode)
            throws Exception {
        ListJid node = getNode();
        int i = 0;
        if (isLeaf()) {
            while (i < node.size()) {
                Jid e = (Jid) node.iGet(i);
                leftNode.append(e.getSerializedBytes(), 1);
                i += 1;
            }
        } else {
            while (i < node.size()) {
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) node.iGet(i);
                leftNode.append(e.getSerializedBytes(), e.size());
                i += 1;
            }
        }
    }

    void append(byte[] bytes, int eSize)
            throws Exception {
        ListJid node = getNode();
        node.iAddBytes(-1, bytes);
        incSize(eSize);
    }
}
