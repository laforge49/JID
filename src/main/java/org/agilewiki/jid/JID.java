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

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.ResponseProcessor;
import org.agilewiki.jactor.bind.*;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.lpc.RequestSource;
import org.agilewiki.jid.requests.*;

/**
 * <p>Base class for Incremental Deserialization Components.</p>
 */
public class JID extends Component {
    /**
     * The JID actor which holds this actor.
     */
    public JID containerJid;
    
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

                internals.bind(GetBytes.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        rp1.process(getBytes());
                    }
                });

                internals.bind(GetJIDComponent.class.getName(), new SyncBinding() {
                    @Override
                    public void acceptRequest(RequestReceiver requestReceiver,
                                              RequestSource requestSource,
                                              Object request,
                                              ResponseProcessor rp1)
                            throws Exception {
                        if (requestReceiver.getMailbox() != requestSource.getMailbox())
                            throw new UnsupportedOperationException("mailboxes are not the same");
                        rp1.process(JID.this);
                    }
                });

                internals.bind(CopyJID.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        CopyJID cj = (CopyJID) request;
                        JCActor thisActor = (JCActor) internals.getThisActor();
                        Actor parent = cj.getParent();
                        if (parent == null)
                            parent = internals.getParent();
                        NewJID nj = new NewJID(thisActor.getActorType(), cj.getMailbox(), parent, getBytes());
                        internals.send(thisActor, nj, rp1);
                    }
                });

                internals.bind(ResolvePathname.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        ResolvePathname rp = (ResolvePathname) request;
                        rp1.process(resolvePathname(rp.getPathname()));
                    }
                });

                internals.bind(PutBytes.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        PutBytes pb = (PutBytes) request;
                        putBytes(pb.getBytes());
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
     * Returns true when the JID has been deserialized.
     *
     * @return True when the JID has been deserialized.
     */
    protected boolean isDeserialized() {
        return true;
    }

    /**
     * Serialize the persistent data.
     *
     * @param mutableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(MutableBytes mutableBytes) {}

    /**
     * Saves the persistent data in a byte array.
     *
     * @param mutableBytes Holds the byte array and offset.
     */
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

    /**
     * Returns a byte array holding the serialized persistent data.
     *
     * @return The byte array holding the serialized persistent data.
     */
    final public byte[] getBytes() {
        byte[] bs = new byte[getSerializedLength()];
        MutableBytes mutableBytes = new MutableBytes(bs, 0);
        save(mutableBytes);
        return bs;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param mutableBytes Holds the serialized data.
     */
    public void load(MutableBytes mutableBytes) {
        serializedData = mutableBytes.immutable();
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param bytes Holds the serialized data.
     */
    final public void putBytes(byte[] bytes) {
        load(new MutableBytes(bytes, 0));
    }

    /**
     * Resolves a JID pathname.
     * 
     * @param pathname A JID pathname.
     * @return A JID actor, or null.
     */
    public JCActor resolvePathname(String pathname) {
        if (pathname != "")
            throw new IllegalArgumentException("Invalid pathname");
        return thisActor;
    }

    /**
     * Notification that the persistent data has changed.
     *
     * @param receiverInternals The internals of the receiving actor.
     * @param lengthChange      The change in the size of the serialized data.
     * @param rp                The response processor.
     * @throws Exception        Any uncaught exception which occurred while processing the notification.
     */
    public void changed(Internals receiverInternals, int lengthChange, ResponseProcessor rp)
            throws Exception {
        serializedData = null;
        if (containerJid == null) {
            rp.process(null);
        }
        containerJid.change(receiverInternals, lengthChange, rp);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param receiverInternals The internals of the receiving actor.
     * @param lengthChange      The change in the size of the serialized data.
     * @param rp                The response processor.
     * @throws Exception        Any uncaught exception which occurred while processing the change.
     */
    public void change(Internals receiverInternals, int lengthChange, ResponseProcessor rp)
            throws Exception {
        changed(receiverInternals, lengthChange, rp);
    }
}
