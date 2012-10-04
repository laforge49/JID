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
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.collection.vlenc.BListJidFactory;
import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.UnionJid;
import org.agilewiki.jid.scalar.vlens.actor.UnionJidFactory;

/**
 * A balanced tree that holds a map.
 */
abstract public class BMapJid<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE extends Jid>
        extends AppJid {
    protected final int TUPLE_SIZE = 0;
    protected final int TUPLE_UNION = 1;
    protected int nodeCapacity = 28;
    protected boolean isRoot;
    protected ActorFactory elementsFactory;
    public ActorFactory valueFactory;

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

    protected UnionJid getUnionJid()
            throws Exception {
        return (UnionJid) _iGet(TUPLE_UNION);
    }

    /**
     * Returns the JidFactory for the key.
     *
     * @return The JidFactory for the key.
     */
    abstract protected ActorFactory getKeyFactory();

    /**
     * Converts a string to a key.
     *
     * @param skey The string to be converted.
     * @return The key.
     */
    abstract protected KEY_TYPE stringToKey(String skey);
}
