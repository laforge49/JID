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

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.JID;

/**
 * A JID component that holds a value.
 */
abstract public class ScalarJid<RESPONSE_TYPE> extends JID {

    /**
     * True when deserialized.
     */
    protected boolean dser = true;

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(GetValue.class.getName(),
                new SynchronousMethodBinding<GetValue<RESPONSE_TYPE>, RESPONSE_TYPE>() {
                    @Override
                    public RESPONSE_TYPE synchronousProcessRequest(Internals internals,
                                                                   GetValue<RESPONSE_TYPE> request)
                            throws Exception {
                        return getValue(internals);
                    }
                });

        thisActor.bind(MakeValue.class.getName(),
                new SynchronousMethodBinding<MakeValue, Boolean>() {
                    @Override
                    public Boolean synchronousProcessRequest(Internals internals, MakeValue request)
                            throws Exception {
                        return makeValue(internals, request);
                    }
                });

        thisActor.bind(SetValue.class.getName(),
                new VoidSynchronousMethodBinding<SetValue>() {
                    @Override
                    public void synchronousProcessRequest(Internals internals, SetValue request)
                            throws Exception {
                        setValue(internals, request);
                    }
                });
    }

    /**
     * Assign a value.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @throws Exception Any uncaught exception raised.
     */
    abstract protected void setValue(Internals internals, SetValue request)
            throws Exception;

    /**
     * Assign a value unless one is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    abstract protected Boolean makeValue(Internals internals, MakeValue request)
            throws Exception;

    /**
     * Returns the value held by this component.
     *
     * @param internals The actor's internals.
     * @return The value held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    abstract protected RESPONSE_TYPE getValue(Internals internals)
            throws Exception;

    /**
     * Returns true when the value has been deserialized.
     *
     * @return True when the value has been deserialized.
     */
    @Override
    protected boolean isDeserialized() {
        return dser;
    }
}
