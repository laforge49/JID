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
import org.agilewiki.jid.*;
import org.agilewiki.jid.scalar.Clearable;
import org.agilewiki.jid.scalar.ScalarJid;

public class UnionJid extends ScalarJid<String, Jid> implements Clearable, Reference {
    protected ActorFactory[] unionFactories;
    protected int factoryIndex = -1;
    protected Jid value;

    protected ActorFactory[] getUnionFactories()
            throws Exception {
        if (unionFactories != null)
            return unionFactories;
        throw new IllegalStateException("unionFactories is null");
    }

    protected int getFactoryIndex(String actorType)
            throws Exception {
        ActorFactory[] uf = getUnionFactories();
        int i = 0;
        while (i < uf.length) {
            if (uf[i].actorType.equals(actorType))
                return i;
            i += 1;
        }
        throw new IllegalArgumentException("Not a valid union type: " + actorType);
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes)
            throws Exception {
        super.load(readableBytes);
        factoryIndex = readableBytes.readInt();
        if (factoryIndex == -1)
            return;
        ActorFactory factory = getUnionFactories()[factoryIndex];
        value = (Jid) factory.newActor(getMailbox(), getParent());
        value.load(readableBytes);
        value.setContainerJid(this);
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() throws Exception {
        if (factoryIndex == -1)
            return Util.INT_LENGTH;
        return Util.INT_LENGTH + value.getSerializedLength();
    }

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void clear() throws Exception {
        setValue(-1);
    }

    @Override
    public void setValue(String actorType)
            throws Exception {
        setValue(getFactoryIndex(actorType));
    }

    @Override
    public void setValue(ActorFactory actorFactory)
            throws Exception {
        setValue(getFactoryIndex(actorFactory.actorType));
    }

    public void setValue(Integer ndx)
            throws Exception {
        int oldLength = getSerializedLength();
        if (value != null)
            value.setContainerJid(null);
        if (ndx == -1) {
            factoryIndex = -1;
            value = null;
        } else {
            ActorFactory factory = getUnionFactories()[ndx];
            factoryIndex = ndx;
            value = (Jid) factory.newActor(getMailbox(), getParent());
            value.setContainerJid(this);
        }
        change(getSerializedLength() - oldLength);
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void setJidBytes(String actorType, byte[] bytes)
            throws Exception {
        setUnionBytes(getFactoryIndex(actorType), bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public void setJidBytes(ActorFactory jidFactory, byte[] bytes)
            throws Exception {
        setUnionBytes(getFactoryIndex(jidFactory.actorType), bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param ndx   The factory index.
     * @param bytes The serialized data.
     * @throws Exception Any uncaught exception raised.
     */
    public void setUnionBytes(Integer ndx, byte[] bytes)
            throws Exception {
        int oldLength = getSerializedLength();
        if (value != null)
            value.setContainerJid(null);
        ActorFactory factory = getUnionFactories()[ndx];
        factoryIndex = ndx;
        value = (Jid) factory.newActor(getMailbox(), getParent());
        value.setContainerJid(this);
        value.load(new ReadableBytes(bytes, 0));
        change(getSerializedLength() - oldLength);
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param jidType The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public Boolean makeValue(String jidType)
            throws Exception {
        return makeUnionValue(getFactoryIndex(jidType));
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param jidFactory The actor type.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    public Boolean makeValue(JidFactory jidFactory)
            throws Exception {
        return makeUnionValue(getFactoryIndex(jidFactory.actorType));
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param ndx The Make request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeUnionValue(Integer ndx)
            throws Exception {
        if (factoryIndex > -1)
            return false;
        setValue(ndx);
        return true;
    }

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param actorType An actor type name.
     * @param bytes     The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeJidBytes(String actorType, byte[] bytes)
            throws Exception {
        return makeUnionBytes(getFactoryIndex(actorType), bytes);
    }

    /**
     * Creates a JID actor and loads its serialized data, unless a JID actor is already present.
     *
     * @param jidFactory The actor type.
     * @param bytes      The serialized data.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    public Boolean makeJidBytes(JidFactory jidFactory, byte[] bytes)
            throws Exception {
        return makeUnionBytes(getFactoryIndex(jidFactory.actorType), bytes);
    }

    public Boolean makeUnionBytes(Integer ndx, byte[] bytes)
            throws Exception {
        if (factoryIndex > -1)
            return false;
        setUnionBytes(ndx, bytes);
        return true;
    }

    @Override
    public Jid getValue() throws Exception {
        return value;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes)
            throws Exception {
        appendableBytes.writeInt(factoryIndex);
        if (factoryIndex == -1)
            return;
        value.save(appendableBytes);
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public _Jid resolvePathname(String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return this;
        }
        if (pathname.equals("0")) {
            return getValue();
        }
        if (pathname.startsWith("0/")) {
            Jid v = getValue();
            if (v == null)
                return null;
            return v.resolvePathname(pathname.substring(2));
        }
        throw new IllegalArgumentException("pathname " + pathname);
    }
}
