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
package org.agilewiki.jid.collection.flenc;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.JidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

/**
 * Defines (StringJidC, StringJidC) tuples.
 */
public class StringStringTupleFactories extends BaseTupleFactories {
    private ActorFactory[] tupleFactories;

    /**
     * Create a LiteActor
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public StringStringTupleFactories(Mailbox mailbox) {
        super(mailbox);
    }

    private ActorFactory[] tupleFactories()
            throws Exception {
        if (tupleFactories != null)
            return tupleFactories;
        tupleFactories = new JidFactory[2];
        ActorFactory jf = StringJidFactory.fac;
        tupleFactories[0] = jf;
        tupleFactories[1] = jf;
        return tupleFactories;
    }

    /**
     * Returns an array of JidFactory.
     *
     * @return An array of JidFactory.
     */
    @Override
    public ActorFactory[] getTupleFactories()
            throws Exception {
        return tupleFactories();
    }
}
