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

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

/**
 * Holds a map with String keys.
 */
public class StringMapJid extends MapJid<String> {
    /**
     * Create a KMake request.
     *
     * @param key The key.
     * @return The KMake request.
     */
    final public static KMake<String> newKMake(String key) {
        return new KMake<String>(key);
    }

    /**
     * Create a KRemove request.
     *
     * @param key The key.
     * @return The KRemove request.
     */
    final public static KRemove<String> newKRemove(String key) {
        return new KRemove<String>(key);
    }

    /**
     * Create a KGet request.
     *
     * @param key The key.
     * @return The KGet request.
     */
    final public static KGet<String> newKGet(String key) {
        return new KGet<String>(key);
    }

    /**
     * Create a GetHigher request.
     *
     * @param key The key.
     * @return The GetHigher request.
     */
    final public static GetHigher<String> newGetHigher(String key) {
        return new GetHigher<String>(key);
    }

    /**
     * Create a GetCeiling request.
     *
     * @param key The key.
     * @return The GetCeiling request.
     */
    final public static GetCeiling<String> newGetCeiling(String key) {
        return new GetCeiling<String>(key);
    }

    /**
     * Create a StringMapJid
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public StringMapJid(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Returns the JidFactory for the key.
     *
     * @return The JidFactory for the key.
     */
    final protected StringJidFactory getKeyFactory() {
        return StringJidFactory.fac;
    }

    /**
     * Converts a string to a key.
     *
     * @param skey The string to be converted.
     * @return The key.
     */
    final protected String stringToKey(String skey) {
        return skey;
    }
}
