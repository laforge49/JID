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
package org.agilewiki.jid;

/**
 * <p>
 * Some common constants and methods.
 * </p>
 */
public class Util {
    /**
     * Size of a boolean in bytes.
     */
    public final static int BOOLEAN_LENGTH = 1;

    /**
     * Size of an int in bytes.
     */
    public final static int INT_LENGTH = 4;

    /**
     * Size of a long in bytes.
     */
    public final static int LONG_LENGTH = 8;

    /**
     * Size of an float in bytes.
     */
    public final static int FLOAT_LENGTH = 4;

    /**
     * Size of an double in bytes.
     */
    public final static int DOUBLE_LENGTH = 8;

    /**
     * Returns the number of bytes needed to write a string.
     *
     * @param length The number of characters in the string.
     * @return The size in bytes.
     */
    public final static int stringLength(int length) {
        if (length == -1)
            return INT_LENGTH;
        if (length > -1)
            return INT_LENGTH + 2 * length;
        throw new IllegalArgumentException("invalid string length: " + length);
    }

    /**
     * Returns the number of bytes needed to write a string.
     *
     * @param s The string.
     * @return The size in bytes.
     */
    public final static int stringLength(String s) {
        if (s == null)
            return INT_LENGTH;
        return stringLength(s.length());
    }
}
