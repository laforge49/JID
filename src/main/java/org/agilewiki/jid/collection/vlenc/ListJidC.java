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
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.collection.CollectionJidC;
import org.agilewiki.jid.jidFactory.JidFactory;

import java.util.ArrayList;

/**
 * Holds an ArrayList of JID actors, all of the same type.
 */
public class ListJidC
        extends CollectionJidC {
    /**
     * Actor type of the elements.
     */
    private JidFactory elementsFactory;

    /**
     * A list of JID actors.
     */
    protected ArrayList<Jid> list;

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    public int size()
            throws Exception {
        initialize();
        return list.size();
    }

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    public Jid iGetJid(int i)
            throws Exception {
        initialize();
        return list.get(i);
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH * 2 + len;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        len = loadLen(readableBytes);
        list = null;
        readableBytes.skip(Util.INT_LENGTH + len);
    }

    /**
     * Returns the JidFactory for all the elements in the list.
     *
     * @return The JidFactory for of all the elements in the list.
     */
    protected JidFactory getListFactory()
            throws Exception {
        return GetValueFactory.req.call(thisActor);
    }

    /**
     * Perform lazy initialization.
     *
     * @throws Exception Any exceptions thrown during initialization.
     */
    protected void initialize()
            throws Exception {
        if (list != null)
            return;
        elementsFactory = getListFactory();
        if (!isSerialized()) {
            list = new ArrayList<Jid>();
            return;
        }
        ReadableBytes readableBytes = readable();
        skipLen(readableBytes);
        int count = readableBytes.readInt();
        list = new ArrayList<Jid>(count);
        int i = 0;
        while (i < count) {
            Jid elementJid = elementsFactory.newJID(thisActor.getMailbox(), thisActor, this, readableBytes);
            list.add(elementJid);
            i += 1;
        }
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
        saveLen(appendableBytes);
        appendableBytes.writeInt(size());
        int i = 0;
        while (i < size()) {
            iGetJid(i).save(appendableBytes);
            i += 1;
        }
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public Actor resolvePathname(String pathname)
            throws Exception {
        initialize();
        return super.resolvePathname(pathname);
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    @Override
    public void iSetBytes(int i, byte[] bytes)
            throws Exception {
        initialize();
        Jid elementJid = elementsFactory.newJID(thisActor.getMailbox(), thisActor, this, bytes);
        Jid oldElementJid = iGetJid(i);
        oldElementJid.setContainerJid(null);
        list.set(i, elementJid);
        change(elementJid.getSerializedLength() - oldElementJid.getSerializedLength());
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(IAddBytes.class.getName(), new VoidSynchronousMethodBinding<IAddBytes>() {
            @Override
            public void synchronousProcessRequest(Internals internals, IAddBytes request)
                    throws Exception {
                iAddBytes(request.getI(), request.getBytes());
            }
        });

        thisActor.bind(IAdd.class.getName(), new VoidSynchronousMethodBinding<IAdd>() {
            @Override
            public void synchronousProcessRequest(Internals internals, IAdd request)
                    throws Exception {
                iAdd(request.getI());
            }
        });

        thisActor.bind(IRemove.class.getName(), new VoidSynchronousMethodBinding<IRemove>() {
            @Override
            public void synchronousProcessRequest(Internals internals, IRemove request)
                    throws Exception {
                iRemove(request.getI());
            }
        });

        thisActor.bind(Empty.class.getName(), new VoidSynchronousMethodBinding<Empty>() {
            @Override
            public void synchronousProcessRequest(Internals internals, Empty request)
                    throws Exception {
                empty();
            }
        });
    }

    /**
     * Creates a JID, loads its bytes and inserts it in the ith position.
     * If i < 0, the new JID is placed at position size + 1 - i.
     * (If i == -1, the element is added to the end of the list.)
     */
    public void iAddBytes(int i, byte[] bytes)
            throws Exception {
        initialize();
        if (i < 0)
            i = size() + 1 + i;
        Jid jid = elementsFactory.newJID(thisActor.getMailbox(), thisActor, this, bytes);
        int c = jid.getSerializedLength();
        list.add(i, jid);
        change(c);
    }

    /**
     * Creates a JID and inserts it in the ith position.
     * If i < 0, the new JID is placed at position size + 1 - i.
     * (If i == -1, the element is added to the end of the list.)
     */
    public void iAdd(int i)
            throws Exception {
        initialize();
        if (i < 0)
            i = size() + 1 + i;
        Jid jid = elementsFactory.newJID(thisActor.getMailbox(), thisActor, this);
        int c = jid.getSerializedLength();
        list.add(i, jid);
        change(c);
    }

    /**
     * Empty a collection.
     */
    public void empty()
            throws Exception {
        int c = 0;
        int i = 0;
        int s = size();
        while (i < s) {
            Jid jid = iGetJid(i);
            jid.setContainerJid(null);
            c -= jid.getSerializedLength();
            i += 1;
        }
        list.clear();
        change(c);
    }

    /**
     * Removes a JID from the ith position.
     * If i < 0, the new JID is removed from position size + 1 - i.
     */
    public void iRemove(int i)
            throws Exception {
        Jid jid = iGetJid(i);
        jid.setContainerJid(null);
        int c = -jid.getSerializedLength();
        list.remove(i);
        change(c);
    }
}
