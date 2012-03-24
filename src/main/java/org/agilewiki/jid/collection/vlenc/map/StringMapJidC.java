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

import org.agilewiki.jid.JidFactories;

/**
 * Holds a map with String keys.
 */
final public class StringMapJidC extends MapJidC<String> {
    /**
     * Create a KMake request.
     *
     * @param key The key.
     * @return The KMake request.
     */
    public static KMake<String> newKMake(String key) {
        return new KMake<String>(key);
    }

    /**
     * Create a KGet request.
     *
     * @param key The key.
     * @return The KGet request.
     */
    public static KGet<String> newKGet(String key) {
        return new KGet<String>(key);
    }

    /**
     * Returns the actor type of the key.
     *
     * @return The actor type of the key.
     */
    protected String getKeyType() {
        return JidFactories.STRING_JID_ATYPE;
    }

    /**
     * Converts a string to a key.
     *
     * @param skey The string to be converted.
     * @return The key.
     */
    String stringToKey(String skey) {
        return skey;
    }
}
