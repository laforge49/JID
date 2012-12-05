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
import org.agilewiki.jid.scalar.flens.integer.IntegerJid;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.UnionJid;

/**
 * A balanced tree that holds a map.
 */
abstract public class BMapJid<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE extends Jid>
        extends AppJid
        implements Collection<MapEntry<KEY_TYPE, VALUE_TYPE>>, JAList, JAMap<KEY_TYPE, VALUE_TYPE> {
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

    protected MapJid<KEY_TYPE, Jid> getNode()
            throws Exception {
        return (MapJid) getUnionJid().getValue();
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
        MapJid<KEY_TYPE, Jid> node = getNode();
        if (isLeaf()) {
            return (MapEntry<KEY_TYPE, VALUE_TYPE>) node.iGet(ndx);
        }
        if (ndx < 0)
            ndx += size();
        if (ndx < 0 || ndx >= size())
            return null;
        int i = 0;
        while (i < node.size()) {
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i).getValue();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void iAdd(int i)
            throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void iAddBytes(int ndx, byte[] bytes)
            throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key   Used to match the first element of the tuples.
     * @param bytes The serialized form of a JID of the appropriate type.
     * @return True if a new tuple was created; otherwise the old value is unaltered.
     */
    public Boolean kMakeBytes(KEY_TYPE key, byte[] bytes)
            throws Exception {
        if (!kMake(key))
            return false;
        kSetBytes(key, bytes);
        return true;
    }

    /**
     * Add an entry to the map unless there is an entry with a matching first element.
     *
     * @param key Used to match the first element of the entries.
     * @return True if a new entry was created.
     */
    @Override
    final public Boolean kMake(KEY_TYPE key)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        if (isLeaf()) {
            int i = node.search(key);
            if (i > -1)
                return false;
            i = -i - 1;
            node.iAdd(i);
            MapEntry<KEY_TYPE, Jid> me = node.iGet(i);
            me.setKey(key);
            incSize(1);
            return true;
        }
        int i = node.match(key);
        MapEntry<KEY_TYPE, Jid> entry = null;
        if (node.size() == i) {
            i -= 1;
            entry = node.iGet(i);
            entry.setKey(key);
        } else {
            entry = node.iGet(i);
        }
        BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) entry.getValue();
        if (!bnode.kMake(key))
            return false;
        incSize(1);
        if (bnode.isFat()) {
            node.iAdd(i - 1);
            MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> leftEntry = (MapEntry) node.iGet(i - 1);
            bnode.inodeSplit(leftEntry);
            if (node.size() < nodeCapacity)
                return true;
            if (isRoot) {
                rootSplit();
            }
        }
        return true;
    }

    protected void rootSplit()
            throws Exception {
        MapJid<KEY_TYPE, Jid> oldRootNode = getNode();
        String oldType = oldRootNode.getActorType();
        getUnionJid().setValue("inode");
        MapJid<KEY_TYPE, Jid> newRootNode = getNode();
        newRootNode.iAdd(0);
        newRootNode.iAdd(1);
        MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> leftEntry = (MapEntry) newRootNode.iGet(0);
        MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> rightEntry = (MapEntry) newRootNode.iGet(1);
        BMapJid<KEY_TYPE, Jid> leftBNode = leftEntry.getValue();
        BMapJid<KEY_TYPE, Jid> rightBNode = rightEntry.getValue();
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
                BMapJid<KEY_TYPE, Jid> e = (BMapJid) oldRootNode.iGet(i).getValue();
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                leftBNode.append(bytes, eSize);
                i += 1;
            }
            while (i < nodeCapacity) {
                BMapJid<KEY_TYPE, Jid> e = (BMapJid) oldRootNode.iGet(i).getValue();
                int eSize = e.size();
                byte[] bytes = e.getSerializedBytes();
                rightBNode.append(bytes, eSize);
                i += 1;
            }
        }
        leftEntry.setKey(leftBNode.getLastKey());
        rightEntry.setKey(rightBNode.getLastKey());
    }

    protected void inodeSplit(MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> leftEntry)
            throws Exception {
        BMapJid<KEY_TYPE, Jid> leftBNode = leftEntry.getValue();
        leftBNode.setNodeType(getNodeType());
        MapJid<KEY_TYPE, Jid> node = getNode();
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
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) node.iGet(0).getValue();
                node.iRemove(0);
                int eSize = e.size();
                incSize(-eSize);
                byte[] bytes = e.getSerializedBytes();
                leftBNode.append(bytes, eSize);
                i += 1;
            }
        }
        KEY_TYPE leftKey = leftBNode.getLastKey();
        leftEntry.setKey(leftKey);
    }

    @Override
    public void empty()
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
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
        MapJid<KEY_TYPE, Jid> node = getNode();
        if (isLeaf()) {
            node.iRemove(ndx);
            incSize(-1);
            return;
        }
        int i = 0;
        while (i < node.size()) {
            MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> entry = (MapEntry) node.iGet(ndx);
            BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) entry.getValue();
            int bns = bnode.size();
            if (ndx < bns) {
                bnode.iRemove(ndx);
                incSize(-1);
                int bnodeSize = bnode.size();
                if (bnodeSize > nodeCapacity / 3) {
                    entry.setKey(bnode.getLastKey());
                    return;
                }
                if (bnodeSize == 0) {
                    node.iRemove(ndx);
                } else {
                    entry.setKey(bnode.getLastKey());
                    if (i > 0) {
                        MapEntry leftEntry = node.iGet(i - 1);
                        BMapJid<KEY_TYPE, VALUE_TYPE> leftBNode = (BMapJid) leftEntry.getValue();
                        if (leftBNode.nodeSize() + bnodeSize < nodeCapacity) {
                            bnode.appendTo(leftBNode);
                            node.iRemove(i);
                            leftEntry.setKey(leftBNode.getLastKey());
                        }
                    }
                    if (i + 1 < node.size()) {
                        MapEntry rightEntry = node.iGet(i + 1);
                        BMapJid<KEY_TYPE, VALUE_TYPE> rightBNode = (BMapJid) rightEntry.getValue();
                        if (bnodeSize + rightBNode.nodeSize() < nodeCapacity) {
                            rightBNode.appendTo(bnode);
                            node.iRemove(i + 1);
                            rightEntry.setKey(rightBNode.getLastKey());
                        }
                    }
                }
                if (node.size() == 1 && isRoot && !isLeaf()) {
                    bnode = (BMapJid) node.iGet(0).getValue();
                    setNodeType(bnode.getNodeType());
                    IntegerJid sj = getSizeJid();
                    sj.setValue(0);
                    bnode.appendTo(this);
                }
                return;
            }
            ndx -= bns;
            i += 1;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Removes the item identified by the key.
     *
     * @param key The key.
     * @return True when the item was present and removed.
     */
    @Override
    final public boolean kRemove(KEY_TYPE key)
            throws Exception {
        if (isLeaf()) {
            MapJid<KEY_TYPE, Jid> node = getNode();
            if (node.kRemove(key)) {
                incSize(-1);
                return true;
            }
            return false;
        }
        MapJid<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> node = (MapJid) getNode();
        int i = node.match(key);
        if (i == size())
            return false;
        MapEntry<KEY_TYPE, BMapJid<KEY_TYPE, Jid>> entry = node.iGet(i);
        BMapJid<KEY_TYPE, Jid> bnode = entry.getValue();
        if (!bnode.kRemove(key))
            return false;
        incSize(-1);
        int bnodeSize = bnode.size();
        if (bnodeSize > nodeCapacity / 3)
            return true;
        if (bnodeSize == 0) {
            node.iRemove(i);
        } else {
            entry.setKey(bnode.getLastKey());
            if (i > 0) {
                MapEntry leftEntry = node.iGet(i - 1);
                BMapJid<KEY_TYPE, VALUE_TYPE> leftBNode = (BMapJid) leftEntry.getValue();
                if (leftBNode.nodeSize() + bnodeSize < nodeCapacity) {
                    bnode.appendTo((BMapJid<KEY_TYPE, Jid>) leftBNode);
                    node.iRemove(i);
                    leftEntry.setKey(leftBNode.getLastKey());
                }
            }
            if (i + 1 < node.size()) {
                MapEntry rightEntry = node.iGet(i + 1);
                BMapJid<KEY_TYPE, VALUE_TYPE> rightBNode = (BMapJid) rightEntry.getValue();
                if (bnodeSize + rightBNode.nodeSize() < nodeCapacity) {
                    rightBNode.appendTo((BMapJid<KEY_TYPE, VALUE_TYPE>) bnode);
                    node.iRemove(i + 1);
                    rightEntry.setKey(rightBNode.getLastKey());
                }
            }
        }
        if (node.size() == 1 && isRoot && !isLeaf()) {
            bnode = (BMapJid) node.iGet(0).getValue();
            setNodeType(bnode.getNodeType());
            IntegerJid sj = getSizeJid();
            sj.setValue(0);
            bnode.appendTo((BMapJid<KEY_TYPE, Jid>) this);
        }
        return true;
    }

    void appendTo(BMapJid<KEY_TYPE, VALUE_TYPE> leftNode)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        int i = 0;
        if (isLeaf()) {
            while (i < node.size()) {
                Jid e = (Jid) node.iGet(i);
                leftNode.append(e.getSerializedBytes(), 1);
                i += 1;
            }
        } else {
            while (i < node.size()) {
                BMapJid<KEY_TYPE, VALUE_TYPE> e = (BMapJid) node.iGet(i).getValue();
                leftNode.append(e.getSerializedBytes(), e.size());
                i += 1;
            }
        }
    }

    void append(byte[] bytes, int eSize)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        node.iAddBytes(-1, bytes);
        incSize(eSize);
    }

    final public MapEntry<KEY_TYPE, VALUE_TYPE> kGetEntry(KEY_TYPE key)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        if (isLeaf()) {
            int i = node.search(key);
            if (i < 0)
                return null;
            return iGet(i);
        }
        int i = node.match(key);
        if (i == size())
            return null;
        BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i).getValue();
        return bnode.kGetEntry(key);
    }

    /**
     * Returns the JID value associated with the key.
     *
     * @param key The key.
     * @return The jid assigned to the key, or null.
     */
    @Override
    final public VALUE_TYPE kGet(KEY_TYPE key)
            throws Exception {
        MapEntry<KEY_TYPE, VALUE_TYPE> entry = kGetEntry(key);
        if (entry == null)
            return null;
        return entry.getValue();
    }

    /**
     * Returns the JID value with the smallest key >= the given key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    @Override
    final public MapEntry<KEY_TYPE, VALUE_TYPE> getCeiling(KEY_TYPE key)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        if (isLeaf()) {
            return (MapEntry<KEY_TYPE, VALUE_TYPE>) node.getCeiling(key);
        }
        int i = node.match(key);
        if (i == size())
            return null;
        BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) node.iGet(i).getValue();
        return bnode.getCeiling(key);
    }

    /**
     * Returns the JID value with a greater key.
     *
     * @param key The key.
     * @return The matching jid, or null.
     */
    @Override
    final public MapEntry<KEY_TYPE, VALUE_TYPE> getHigher(KEY_TYPE key)
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        MapEntry entry = node.getHigher(key);
        if (isLeaf())
            return (MapEntry<KEY_TYPE, VALUE_TYPE>) entry;
        if (entry == null)
            return null;
        BMapJid<KEY_TYPE, VALUE_TYPE> bnode = (BMapJid) entry.getValue();
        return bnode.getHigher(key);
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    final public _Jid resolvePathname(String pathname)
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
        _Jid jid = kGet(stringToKey(ns));
        if (jid == null)
            return null;
        if (s == pathname.length())
            return jid;
        return jid.resolvePathname(pathname.substring(s + 1));
    }

    public MapEntry<KEY_TYPE, VALUE_TYPE> getFirst()
            throws Exception {
        return iGet(0);
    }

    public MapEntry<KEY_TYPE, VALUE_TYPE> getLast()
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        return (MapEntry<KEY_TYPE, VALUE_TYPE>) node.getLast();
    }

    public KEY_TYPE getLastKey()
            throws Exception {
        MapJid<KEY_TYPE, Jid> node = getNode();
        return node.getLastKey();
    }

    @Override
    public void kSetBytes(KEY_TYPE key, byte[] bytes)
            throws Exception {
        MapEntry<KEY_TYPE, VALUE_TYPE> entry = kGetEntry(key);
        if (entry == null)
            throw new IllegalArgumentException("not present: " + key);
        entry.setValueBytes(bytes);
    }
}
