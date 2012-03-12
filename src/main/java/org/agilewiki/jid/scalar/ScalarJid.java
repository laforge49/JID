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
import org.agilewiki.jid.JidC;

/**
 * A JID component that holds a value.
 */
abstract public class ScalarJid<RESPONSE_TYPE> extends JidC {

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
                        return getValue();
                    }
                });

        thisActor.bind(SetValue.class.getName(),
                new VoidSynchronousMethodBinding<SetValue>() {
                    @Override
                    public void synchronousProcessRequest(Internals internals, SetValue request)
                            throws Exception {
                        setValue(request);
                    }
                });
    }

    /**
     * Assign a value.
     *
     * @param request The MakeValue request.
     * @throws Exception Any uncaught exception raised.
     */
    abstract protected void setValue(SetValue request)
            throws Exception;

    /**
     * Returns the value held by this component.
     *
     * @return The value held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    abstract protected RESPONSE_TYPE getValue()
            throws Exception;
}
