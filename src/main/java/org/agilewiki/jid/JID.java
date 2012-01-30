/*
 * Copyright 2011 Bill La Forge
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

import org.agilewiki.jactor.ResponseProcessor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.MethodBinding;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jid.requests.GetSerializedLength;
import org.agilewiki.jid.requests.Save;

/**
 * <p>Base class for Incremental Deserialization Components.</p>
 */
public class JID extends Component {
    /**
     * The serialized form of the persistent data, or null. 
     */
    protected ImmutableBytes serializedData;

    /**
     * Initialize the component after all its includes have been processed.
     * The response must always be null;
     *
     * @param internals The JBActor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    @Override
    public void open(final Internals internals, final ResponseProcessor rp) throws Exception {
        super.open(internals, new ResponseProcessor() {
            @Override
            public void process(Object response) throws Exception {
                internals.bind(GetSerializedLength.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        rp1.process(getSerializedLength());
                    }
                });

                internals.bind(Save.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        Save s = (Save) request;
                        save(s.getMutableBytes());
                        rp1.process(null);
                    }
                });

                rp.process(null);
            }
        });
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    public int getSerializedLength() {
        return 0;
    }

    /**
     * Returns true when the persistent data is already serialized.
     * 
     * @return True when the persistent data is already serialized.
     */
    final protected boolean isSerialized() {
        return serializedData != null;
    }

    /**
     * Serialize the persistent data.
     * 
     * @param mutableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(MutableBytes mutableBytes) {
        serializedData = new ImmutableBytes(new byte[0], 0);
    }
    
    final public void save(MutableBytes mutableBytes) {
        if (isSerialized()) {
            ImmutableBytes sd = mutableBytes.immutable();
            mutableBytes.writeImmutableBytes(serializedData, getSerializedLength());
            serializedData = sd;
        } else {
            serializedData = mutableBytes.immutable();
            serialize(mutableBytes);
        }
        if (serializedData.getOffset() + getSerializedLength() != mutableBytes.getOffset()) {
            System.err.println("\n" + getClass().getName());
            System.err.println("" + serializedData.getOffset() + " + " + getSerializedLength() + " != " + mutableBytes.getOffset());
            throw new IllegalStateException();
        }
    }
}
