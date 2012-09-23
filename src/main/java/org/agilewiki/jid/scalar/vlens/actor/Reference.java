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
package org.agilewiki.jid.scalar.vlens.actor;

import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.JidFactory;
import org.agilewiki.jid.scalar.Scalar;

public interface Reference extends Scalar<String, Jid> {

    /**
     * Assign a value.
     *
     * @param jidFactory The actor type.
     * @throws Exception Any uncaught exception raised.
     */
    public void setValue(ActorFactory jidFactory)
            throws Exception;

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    public void setJidBytes(String actorType, byte[] bytes)
            throws Exception;

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    public void setJidBytes(ActorFactory jidFactory, byte[] bytes)
            throws Exception;

    /**
     * Assign a value unless one is already present.
     *
     * @param jidType The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeValue(String jidType)
            throws Exception;

    /**
     * Assign a value unless one is already present.
     *
     * @param jidFactory The actor type.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeValue(JidFactory jidFactory)
            throws Exception;

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeJidBytes(String actorType, byte[] bytes)
            throws Exception;

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeJidBytes(JidFactory jidFactory, byte[] bytes)
            throws Exception;
}
