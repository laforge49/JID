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
package org.agilewiki.jid.scalar;

import org.agilewiki.jactor.bind.SynchronousRequest;

/**
 * Assigns a value or creates an actor if there was no previous value.
 * Returns true if successful.
 */
public class MakeValue<VALUE_TYPE> extends SynchronousRequest<Boolean> {
    /**
     * The value (or actor type).
     */
    private VALUE_TYPE value;

    /**
     * Returns the value (or actor type).
     *
     * @return The value (or actor type).
     */
    public VALUE_TYPE getValue() {
        return value;
    }

    /**
     * Creates the request.
     *
     * @param value The value (or actor type).
     */
    public MakeValue(VALUE_TYPE value) {
        this.value = value;
    }
}
