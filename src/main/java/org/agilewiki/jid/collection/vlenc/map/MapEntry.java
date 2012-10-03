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
import org.agilewiki.jid.collection.flenc.AppJid;
import org.agilewiki.jid.scalar.ScalarJid;

/**
 * A map is, in part, a list of map entries.
 */
public class MapEntry<KEY_TYPE extends Comparable, VALUE_TYPE>
        extends AppJid
        implements ComparableKey {

    void setFactories(ActorFactory keyFactory, ActorFactory valueFactory) {
        tupleFactories = new ActorFactory[2];
        tupleFactories[0] = keyFactory;
        tupleFactories[1] = valueFactory;
    }

    public KEY_TYPE getKey()
            throws Exception {
        return (KEY_TYPE) ((ScalarJid) _iGet(0)).getValue();
    }

    public void setKey(KEY_TYPE key)
            throws Exception {
        ((ScalarJid) _iGet(0)).setValue(key);
    }

    public VALUE_TYPE getValue()
            throws Exception {
        return (VALUE_TYPE) _iGet(1);
    }

    /**
     * Compares the key or value;
     *
     * @param o The comparison value.
     * @return The result of a compareTo(o).
     */
    @Override
    public int compareKeyTo(Object o) throws Exception {
        return getKey().compareTo(o);
    }
}
