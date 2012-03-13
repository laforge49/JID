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
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.bind.*;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.requests.Save;

import java.util.Arrays;

/**
 * Base class for Incremental Deserialization Components.
 */
public class JidC extends Component implements Jid {
    /**
     * The JID actor which holds this actor.
     */
    private Jid containerJid;

    /**
     * Holds the serialized data.
     */
    protected byte[] serializedBytes;

    /**
     * The start of the serialized data.
     */
    protected int serializedOffset;

    /**
     * Returns the actor.
     *
     * @return The actor.
     */
    final public JCActor thisActor() {
        return thisActor;
    }

    /**
     * Returns the actor type.
     *
     * @return The actor type, or null.
     */
    @Override
    final public String getActorType() {
        return thisActor.getActorType();
    }

    /**
     * Assign the container.
     *
     * @param containerJid The container, or null.
     */
    @Override
    final public void setContainerJid(Jid containerJid) {
        this.containerJid = containerJid;
    }

    /**
     * Returns a readable form of the serialized data.
     *
     * @return A ReadableBytes wrapper of the serialized data.
     */
    final protected ReadableBytes readable() {
        return new ReadableBytes(serializedBytes, serializedOffset);
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {

        thisActor.bind(GetSerializedLength.class.getName(),
                new SynchronousMethodBinding<GetSerializedLength, Integer>() {
                    @Override
                    public Integer synchronousProcessRequest(Internals internals, GetSerializedLength request)
                            throws Exception {
                        return getSerializedLength();
                    }
                });

        thisActor.bind(Save.class.getName(), new VoidSynchronousMethodBinding<Save>() {
            @Override
            public void synchronousProcessRequest(Internals internals, Save request) throws Exception {
                save(request.getAppendableBytes());
            }
        });

        thisActor.bind(GetBytes.class.getName(), new SynchronousMethodBinding<GetBytes, byte[]>() {
            @Override
            public byte[] synchronousProcessRequest(Internals internals, GetBytes request) throws Exception {
                return getBytes();
            }
        });

        thisActor.bind(
                GetJIDComponent.class.getName(),
                new InitializationMethodBinding<GetJIDComponent, JidC>() {
                    @Override
                    public JidC initializationProcessRequest(Internals internals, GetJIDComponent request)
                            throws Exception {
                        return JidC.this;
                    }
                });

        thisActor.bind(CopyJID.class.getName(), new SynchronousMethodBinding<CopyJID, Actor>() {
            @Override
            public Actor synchronousProcessRequest(Internals internals, CopyJID request) throws Exception {
                Mailbox m = request.getMailbox();
                if (m == null)
                    m = thisActor.getMailbox();
                return (new NewJID(thisActor.getActorType(), m, getBytes())).call(thisActor).thisActor();
            }
        });

        thisActor.bind(ResolvePathname.class.getName(), new SynchronousMethodBinding<ResolvePathname, Actor>() {
            @Override
            public Actor synchronousProcessRequest(Internals internals, ResolvePathname request) throws Exception {
                return resolvePathname(request.getPathname());
            }
        });

        thisActor.bind(PutBytes.class.getName(), new VoidInitializationMethodBinding<PutBytes>() {
            @Override
            public void initializationProcessRequest(Internals internals, PutBytes request) throws Exception {
                putBytes(request.getBytes());
            }
        });

        thisActor.bind(GetJidClassName.class.getName(), new ConcurrentMethodBinding<GetJidClassName, String>() {
            @Override
            public String concurrentProcessRequest(RequestReceiver requestReceiver, GetJidClassName request)
                    throws Exception {
                return JidC.this.getClass().getName();
            }
        });

        thisActor.bind(IsJidEqual.class.getName(), new MethodBinding<IsJidEqual, Boolean>() {
            @Override
            public void processRequest(final Internals internals, IsJidEqual request, final RP<Boolean> rp)
                    throws Exception {
                Actor actor = request.getJidActor();
                if (!(actor instanceof JCActor)) {
                    rp.processResponse(false);
                    return;
                }
                final JCActor jcActor = (JCActor) actor;
                if (!GetJidClassName.req.call(jcActor).equals(JidC.this.getClass().getName())) {
                    rp.processResponse(false);
                    return;
                }
                GetSerializedLength.req.send(internals, jcActor, new RP<Integer>() {
                    @Override
                    public void processResponse(Integer response) throws Exception {
                        if (response.intValue() != getSerializedLength()) {
                            rp.processResponse(false);
                            return;
                        }
                        GetBytes.req.send(internals, jcActor, new RP<byte[]>() {
                            @Override
                            public void processResponse(byte[] response) throws Exception {
                                boolean eq = Arrays.equals(response, getBytes());
                                rp.processResponse(eq);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return 0;
    }

    /**
     * Returns true when the persistent data is already serialized.
     *
     * @return True when the persistent data is already serialized.
     */
    final protected boolean isSerialized() {
        return serializedBytes != null;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(AppendableBytes appendableBytes) {
    }

    /**
     * Saves the persistent data in a byte array.
     *
     * @param appendableBytes Holds the byte array and offset.
     */
    @Override
    final public void save(AppendableBytes appendableBytes) {
        if (isSerialized()) {
            byte[] bs = appendableBytes.getBytes();
            int off = appendableBytes.getOffset();
            appendableBytes.writeBytes(serializedBytes, serializedOffset, getSerializedLength());
            serializedBytes = bs;
            serializedOffset = off;
        } else {
            serializedBytes = appendableBytes.getBytes();
            serializedOffset = appendableBytes.getOffset();
            serialize(appendableBytes);
        }
        if (serializedOffset + getSerializedLength() != appendableBytes.getOffset()) {
            System.err.println("\n" + getClass().getName());
            System.err.println("" + serializedOffset +
                    " + " + getSerializedLength() + " != " + appendableBytes.getOffset());
            throw new IllegalStateException();
        }
    }

    /**
     * Returns a byte array holding the serialized persistent data.
     *
     * @return The byte array holding the serialized persistent data.
     */
    final private byte[] getBytes() {
        byte[] bs = new byte[getSerializedLength()];
        AppendableBytes appendableBytes = new AppendableBytes(bs, 0);
        save(appendableBytes);
        return bs;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        if (thisActor.isOpen())
            throw new IllegalStateException("Already active");
        serializedBytes = readableBytes.getBytes();
        serializedOffset = readableBytes.getOffset();
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param bytes Holds the serialized data.
     */
    final private void putBytes(byte[] bytes) {
        load(new ReadableBytes(bytes, 0));
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    public Actor resolvePathname(String pathname)
            throws Exception {
        if (pathname != "")
            throw new IllegalArgumentException("pathname " + pathname);
        return thisActor;
    }

    /**
     * Notification that the persistent data has changed.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the notification.
     */
    protected void changed(int lengthChange)
            throws Exception {
        serializedBytes = null;
        serializedOffset = -1;
        if (containerJid == null)
            return;
        containerJid.change(lengthChange);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(int lengthChange)
            throws Exception {
        changed(lengthChange);
    }

    @Override
    final public boolean equals(Object v) {
        if (v == null)
            return false;
        if (!v.getClass().equals(getClass()))
            return false;
        JidC jid = (JidC) v;
        if (jid.getSerializedLength() != getSerializedLength())
            return false;
        return Arrays.equals(jid.getBytes(), getBytes());
    }

    @Override
    final public int hashCode() {
        return getBytes().hashCode();
    }
}
