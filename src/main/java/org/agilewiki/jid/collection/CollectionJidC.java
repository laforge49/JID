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
package org.agilewiki.jid.collection;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jid.*;

/**
 * A collection of JID actors.
 */
abstract public class CollectionJidC
        extends JidC
        implements Collection {

    /**
     * The size of the serialized data (exclusive of its length header).
     */
    protected int len;

    /**
     * Skip over the length at the beginning of the serialized data.
     *
     * @param readableBytes Holds the serialized data.
     */
    protected void skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }

    /**
     * Returns the size of the serialized data (exclusive of its length header).
     *
     * @param readableBytes Holds the serialized data.
     * @return The size of the serialized data (exclusive of its length header).
     */
    protected int loadLen(ReadableBytes readableBytes) {
        return readableBytes.readInt();
    }

    /**
     * Writes the size of the serialized data (exclusive of its length header).
     *
     * @param appendableBytes The object written to.
     */
    protected void saveLen(AppendableBytes appendableBytes) {
        appendableBytes.writeInt(len);
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
        len += lengthChange;
        super.change(lengthChange);
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
        if (pathname.length() == 0) {
            return thisActor;
        }
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        int n = 0;
        try {
            n = Integer.parseInt(ns);
        } catch (Exception ex) {
            throw new IllegalArgumentException("pathname " + pathname);
        }
        if (n < 0 || n >= size())
            throw new IllegalArgumentException("pathname " + pathname);
        Jid jid = get(n);
        if (s == pathname.length())
            return jid.thisActor();
        return jid.resolvePathname(pathname.substring(s + 1));
    }

    /**
     * Returns the selected element.
     *
     * @param ndx Selects the element.
     * @return The actor held by the selected element.
     */
    @Override
    public Actor iGet(int ndx)
            throws Exception {
        return get(ndx).thisActor();
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(Size.class.getName(), new SynchronousMethodBinding<Size, Integer>() {
            @Override
            public Integer synchronousProcessRequest(Internals internals, Size request) throws Exception {
                return size();
            }
        });

        thisActor.bind(IGet.class.getName(), new SynchronousMethodBinding<IGet, Actor>() {
            @Override
            public Actor synchronousProcessRequest(Internals internals, IGet request)
                    throws Exception {
                int ndx = request.getI();
                return get(ndx).thisActor();
            }
        });

        thisActor.bind(ISetBytes.class.getName(), new VoidSynchronousMethodBinding<ISetBytes>() {
            @Override
            public void synchronousProcessRequest(Internals internals, ISetBytes request)
                    throws Exception {
                iSetBytes(request.getI(), request.getBytes());
            }
        });
    }
}
