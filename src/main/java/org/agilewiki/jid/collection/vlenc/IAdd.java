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

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

/**
 * Creates a JID and inserts it in the ith position.
 * If i < 0, the new JID is placed at position size + 1 - i.
 * (If i == -1, the element is added to the end of the list.)
 */
public class IAdd extends Request<Object, JAList> {
    /**
     * The insertion index of the new element.
     */
    private int i;

    /**
     * Returns the insertion index of the new element.
     *
     * @return The insertion index of the new element.
     */
    public int getI() {
        return i;
    }

    /**
     * Create the request.
     *
     * @param i The insertion index of the new element.
     */
    public IAdd(int i) {
        this.i = i;
    }

    /**
     * Returns true when targetActor is an instanceof TARGET_TYPE
     *
     * @param targetActor The actor to be called.
     * @return True when targetActor is an instanceof TARGET_TYPE.
     */
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof JAList;
    }

    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        ((JAList) targetActor).iAdd(i);
        rp.processResponse(null);
    }
}
