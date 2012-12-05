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
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.scalar.ScalarJid;

/**
 * A map is, in part, a list of map entries.
 */
public class MapEntry<KEY_TYPE extends Comparable<KEY_TYPE>, VALUE_TYPE>
        extends AppJid
        implements ComparableKey<KEY_TYPE> {

    private final static int TUPLE_KEY = 0;
    private final static int TUPLE_VALUE = 1;

    void setFactories(ActorFactory keyFactory, ActorFactory valueFactory) {
        tupleFactories = new ActorFactory[2];
        tupleFactories[TUPLE_KEY] = keyFactory;
        tupleFactories[TUPLE_VALUE] = valueFactory;
    }

    public KEY_TYPE getKey()
            throws Exception {
        return (KEY_TYPE) ((ScalarJid) _iGet(TUPLE_KEY)).getValue();
    }

    protected void setKey(KEY_TYPE key)
            throws Exception {
        ((ScalarJid) _iGet(TUPLE_KEY)).setValue(key);
    }

    public VALUE_TYPE getValue()
            throws Exception {
        return (VALUE_TYPE) _iGet(TUPLE_VALUE);
    }

    public void setValueBytes(byte[] bytes) throws Exception {
        Jid old = (Jid) getValue();
        old.setContainerJid(null);
        Jid elementJid = createSubordinate(tupleFactories[TUPLE_VALUE], this, bytes);
        tuple[TUPLE_VALUE] = elementJid;
        change(elementJid.getSerializedLength() - old.getSerializedLength());
    }

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(KEY_TYPE o) throws Exception {
        return getKey().compareTo(o);
    }
}
