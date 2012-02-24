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
import org.agilewiki.jactor.bind.*;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.jidFactory.NewJID;
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
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {

        thisActor.bind(GetSerializedLength.class.getName(),
                new SynchronousMethodBinding<GetSerializedLength, Integer>() {
                    @Override
                    public Integer synchronousProcessRequest(Internals internals, GetSerializedLength request) throws Exception {
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

        thisActor.bind(GetJIDComponent.class.getName(), new SynchronousOnlyMethodBinding<GetJIDComponent, JID>() {
            @Override
            public JID synchronousProcessRequest(Internals internals, GetJIDComponent request) throws Exception {
                return JID.this;
            }
        });

        thisActor.bind(CopyJID.class.getName(), new SynchronousMethodBinding<CopyJID, JCActor>() {
            @Override
            public JCActor synchronousProcessRequest(Internals internals, CopyJID request) throws Exception {
                JCActor thisActor = (JCActor) internals.getThisActor();
                Actor parent = request.getParent();
                if (parent == null)
                    parent = thisActor.getParent();
                return (new NewJID(thisActor.getActorType(), request.getMailbox(), parent, getBytes())).call(thisActor);
            }
        });

        thisActor.bind(ResolvePathname.class.getName(), new SynchronousMethodBinding<ResolvePathname, JCActor>() {
            @Override
            public JCActor synchronousProcessRequest(Internals internals, ResolvePathname request) throws Exception {
                return resolvePathname(internals, request.getPathname());
            }
        });

        thisActor.bind(PutBytes.class.getName(), new VoidInitializationMethodBinding<PutBytes>() {
            @Override
            public void initializationProcessRequest(PutBytes request) throws Exception {
                putBytes(request.getBytes());
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
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(AppendableBytes appendableBytes) {
    }

    /**
     * Saves the persistent data in a byte array.
     *
     * @param appendableBytes Holds the byte array and offset.
     */
    final public void save(AppendableBytes appendableBytes) {
        if (isSerialized()) {
            ImmutableBytes sd = appendableBytes.immutable();
            appendableBytes.writeImmutableBytes(serializedData, getSerializedLength());
            serializedData = sd;
        } else {
            serializedData = appendableBytes.immutable();
            serialize(appendableBytes);
        }
        if (serializedData.getOffset() + getSerializedLength() != appendableBytes.getOffset()) {
            System.err.println("\n" + getClass().getName());
            System.err.println("" + serializedData.getOffset() +
                    " + " + getSerializedLength() + " != " + appendableBytes.getOffset());
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
        AppendableBytes appendableBytes = new AppendableBytes(bs, 0);
        save(appendableBytes);
        return bs;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    public void load(ReadableBytes readableBytes) {
        serializedData = readableBytes.immutable();
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param bytes Holds the serialized data.
     */
    final public void putBytes(byte[] bytes) {
        load(new ReadableBytes(bytes, 0));
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param internals The actor's internals.
     * @param pathname  A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    public JCActor resolvePathname(Internals internals, String pathname)
            throws Exception {
        if (pathname != "")
            throw new IllegalArgumentException("Invalid pathname");
        return thisActor;
    }

    /**
     * Notification that the persistent data has changed.
     *
     * @param internals    The actor's internals.
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the notification.
     */
    public void changed(Internals internals, int lengthChange)
            throws Exception {
        serializedData = null;
        if (containerJid == null)
            return;
        containerJid.change(internals, lengthChange);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param internals    The actor's internals.
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    public void change(Internals internals, int lengthChange)
            throws Exception {
        changed(internals, lengthChange);
    }
}
