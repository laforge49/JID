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
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jactor.factory.JAFactoryFactory;
import org.agilewiki.jactor.factory.NewActor;
import org.agilewiki.jactor.factory.Requirement;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jid.collection.flenc.TupleJidFactory;
import org.agilewiki.jid.collection.vlenc.*;
import org.agilewiki.jid.collection.vlenc.map.integer.*;
import org.agilewiki.jid.collection.vlenc.map.lng.*;
import org.agilewiki.jid.collection.vlenc.map.string.*;
import org.agilewiki.jid.jidsFactory.JidsFactoryFactory;
import org.agilewiki.jid.jidsFactory.NewJID;
import org.agilewiki.jid.scalar.flens.bool.BooleanJidFactory;
import org.agilewiki.jid.scalar.flens.dbl.DoubleJidFactory;
import org.agilewiki.jid.scalar.flens.flt.FloatJidFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.flens.lng.LongJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.RootJidFactory;
import org.agilewiki.jid.scalar.vlens.bytes.BytesJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

/**
 * <p>
 * Defines Jid actor types and registers the JID factories.
 * </p>
 */
final public class JidFactories extends JLPCActor {

    /**
     * The name of the JID actor.
     */
    public final static String JID_TYPE = "JID";

    /**
     * The name of the BooleanJid actor.
     */
    public final static String BOOLEAN_JID_TYPE = "BOOLEAN_JID";

    /**
     * The name of the IntegerJid actor.
     */
    public final static String INTEGER_JID_TYPE = "INT_JID";

    /**
     * The name of the LongJid actor.
     */
    public final static String LONG_JID_TYPE = "LONG_JID";

    /**
     * The name of the FloatJid actor.
     */
    public final static String FLOAT_JID_TYPE = "FLOAT_JID";

    /**
     * The name of the DoubleJid actor.
     */
    public final static String DOUBLE_JID_TYPE = "DOUBLE_JID";

    /**
     * The name of the JidJid actor.
     */
    public final static String ACTOR_JID_TYPE = "ACTOR_JID";

    /**
     * The name of the JidJid actor.
     */
    public final static String ROOT_JID_TYPE = "ROOT_JID";

    /**
     * The name of the String actor.
     */
    public final static String STRING_JID_TYPE = "STRING_JID";

    /**
     * The name of the BytesJid actor.
     */
    public final static String BYTES_JID_TYPE = "BYTES_JID";

    /**
     * The name of the TupleJid actor.
     */
    public final static String TUPLE_JID_TYPE = "TUPLE_JID";

    /**
     * The name of the StringListJid actor.
     */
    public final static String STRING_LIST_JID_TYPE = "STRING_LIST_JID";

    /**
     * The name of the BytesListJid actor.
     */
    public final static String BYTES_LIST_JID_TYPE = "BYTES_LIST_JID";

    /**
     * The name of the ActorListJid actor.
     */
    public final static String ACTOR_LIST_JID_TYPE = "ACTOR_LIST_JID";

    /**
     * The name of the LongListJid actor.
     */
    public final static String LONG_LIST_JID_TYPE = "LONG_LIST_JID";

    /**
     * The name of the IntegerListJid actor.
     */
    public final static String INTEGER_LIST_JID_TYPE = "INTEGER_LIST_JID";

    /**
     * The name of the FloatListJid actor.
     */
    public final static String FLOAT_LIST_JID_TYPE = "FLOAT_LIST_JID";

    /**
     * The name of the DoubleListJid actor.
     */
    public final static String DOUBLE_LIST_JID_TYPE = "DOUBLE_LIST_JID";

    /**
     * The name of the BooleanListJid actor.
     */
    public final static String BOOLEAN_LIST_JID_TYPE = "BOOLEAN_LIST_JID";

    /**
     * The name of the StringStringMapJid actor.
     */
    public final static String STRING_STRING_MAP_JID_TYPE = "STRING_STRING_MAP_JID";

    /**
     * The name of the StringBytesMapJid actor.
     */
    public final static String STRING_BYTES_MAP_JID_TYPE = "STRING_BYTES_MAP_JID";

    /**
     * The name of the StringActorMapJid actor.
     */
    public final static String STRING_ACTOR_MAP_JID_TYPE = "STRING_ACTOR_MAP_JID";

    /**
     * The name of the StringLongMapJid actor.
     */
    public final static String STRING_LONG_MAP_JID_TYPE = "STRING_LONG_MAP_JID";

    /**
     * The name of the StringIntegerMapJid actor.
     */
    public final static String STRING_INTEGER_MAP_JID_TYPE = "STRING_INTEGER_MAP_JID";

    /**
     * The name of the StringFloatMapJid actor.
     */
    public final static String STRING_FLOAT_MAP_JID_TYPE = "STRING_FLOAT_MAP_JID";

    /**
     * The name of the StringDoubleMapJid actor.
     */
    public final static String STRING_DOUBLE_MAP_JID_TYPE = "STRING_DOUBLE_MAP_JID";

    /**
     * The name of the StringBooleanMapJid actor.
     */
    public final static String STRING_BOOLEAN_MAP_JID_TYPE = "STRING_BOOLEAN_MAP_JID";

    /**
     * The name of the IntegerStringMapJid actor.
     */
    public final static String INTEGER_STRING_MAP_JID_TYPE = "INTEGER_STRING_MAP_JID";

    /**
     * The name of the IntegerBytesMapJid actor.
     */
    public final static String INTEGER_BYTES_MAP_JID_TYPE = "INTEGER_BYTES_MAP_JID";

    /**
     * The name of the IntegerActorMapJid actor.
     */
    public final static String INTEGER_ACTOR_MAP_JID_TYPE = "INTEGER_ACTOR_MAP_JID";

    /**
     * The name of the IntegerLongMapJid actor.
     */
    public final static String INTEGER_LONG_MAP_JID_TYPE = "INTEGER_LONG_MAP_JID";

    /**
     * The name of the IntegerIntegerMapJid actor.
     */
    public final static String INTEGER_INTEGER_MAP_JID_TYPE = "INTEGER_INTEGER_MAP_JID";

    /**
     * The name of the IntegerFloatMapJid actor.
     */
    public final static String INTEGER_FLOAT_MAP_JID_TYPE = "INTEGER_FLOAT_MAP_JID";

    /**
     * The name of the IntegerDoubleMapJid actor.
     */
    public final static String INTEGER_DOUBLE_MAP_JID_TYPE = "INTEGER_DOUBLE_MAP_JID";

    /**
     * The name of the IntegerBooleanMapJid actor.
     */
    public final static String INTEGER_BOOLEAN_MAP_JID_TYPE = "INTEGER_BOOLEAN_MAP_JID";

    /**
     * The name of the LongStringMapJid actor.
     */
    public final static String LONG_STRING_MAP_JID_TYPE = "LONG_STRING_MAP_JID";

    /**
     * The name of the LongBytesMapJid actor.
     */
    public final static String LONG_BYTES_MAP_JID_TYPE = "LONG_BYTES_MAP_JID";

    /**
     * The name of the LongActorMapJid actor.
     */
    public final static String LONG_ACTOR_MAP_JID_TYPE = "LONG_ACTOR_MAP_JID";

    /**
     * The name of the LongLongMapJid actor.
     */
    public final static String LONG_LONG_MAP_JID_TYPE = "LONG_LONG_MAP_JID";

    /**
     * The name of the LongIntegerMapJid actor.
     */
    public final static String LONG_INTEGER_MAP_JID_TYPE = "LONG_INTEGER_MAP_JID";

    /**
     * The name of the LongFloatMapJid actor.
     */
    public final static String LONG_FLOAT_MAP_JID_TYPE = "LONG_FLOAT_MAP_JID";

    /**
     * The name of the LongDoubleMapJid actor.
     */
    public final static String LONG_DOUBLE_MAP_JID_TYPE = "LONG_DOUBLE_MAP_JID";

    /**
     * The name of the LongBooleanMapJid actor.
     */
    public final static String LONG_BOOLEAN_MAP_JID_TYPE = "LONG_BOOLEAN_MAP_JID";

    /**
     * Create a LiteActor
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public JidFactories(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Returns the actor's requirements.
     *
     * @return The actor's requirents.
     */
    @Override
    protected Requirement[] requirements() throws Exception {
        Requirement[] requirements = new Requirement[2];
        requirements[0] = new Requirement(
                new NewActor(""),
                new JAFactoryFactory(JAFactoryFactory.TYPE));
        requirements[1] = new Requirement(
                new NewJID(""),
                new JidsFactoryFactory(JidsFactoryFactory.TYPE));
        return requirements;
    }

    /**
     * Process the requirements and assign the parent actor.
     * Once assigned, it can not be changed.
     *
     * @param parent The parent actor.
     */
    @Override
    public void setParent(Actor parent) throws Exception {
        if (parent == null) {
            parent = new JAFactory(getMailbox());
        }
        super.setParent(parent);

        Actor f = parent;
        while (!(f instanceof JAFactory)) f = f.getParent();
        JAFactory factory = (JAFactory) f;

        factory.registerActorFactory(JidFactory.fac);

        factory.registerActorFactory(BooleanJidFactory.fac);
        factory.registerActorFactory(IntegerJidFactory.fac);
        factory.registerActorFactory(new LongJidFactory());
        factory.registerActorFactory(new FloatJidFactory());
        factory.registerActorFactory(new DoubleJidFactory());

        factory.registerActorFactory(new ActorJidFactory());
        factory.registerActorFactory(new RootJidFactory());
        factory.registerActorFactory(new StringJidFactory());
        factory.registerActorFactory(new BytesJidFactory());

        factory.registerActorFactory(new TupleJidFactory());

        factory.registerActorFactory(new StringListJidFactory());
        factory.registerActorFactory(new BytesListJidFactory());
        factory.registerActorFactory(new ActorListJidFactory());
        factory.registerActorFactory(new LongListJidFactory());
        factory.registerActorFactory(new IntegerListJidFactory());
        factory.registerActorFactory(new FloatListJidFactory());
        factory.registerActorFactory(new DoubleListJidFactory());
        factory.registerActorFactory(new BooleanListJidFactory());

        factory.registerActorFactory(new StringStringMapJidFactory());
        factory.registerActorFactory(new StringBytesMapJidFactory());
        factory.registerActorFactory(new StringActorMapJidFactory());
        factory.registerActorFactory(new StringLongMapJidFactory());
        factory.registerActorFactory(new StringIntegerMapJidFactory());
        factory.registerActorFactory(new StringFloatMapJidFactory());
        factory.registerActorFactory(new StringDoubleMapJidFactory());
        factory.registerActorFactory(new StringBooleanMapJidFactory());

        factory.registerActorFactory(new IntegerStringMapJidFactory());
        factory.registerActorFactory(new IntegerBytesMapJidFactory());
        factory.registerActorFactory(new IntegerActorMapJidFactory());
        factory.registerActorFactory(new IntegerLongMapJidFactory());
        factory.registerActorFactory(new IntegerIntegerMapJidFactory());
        factory.registerActorFactory(new IntegerFloatMapJidFactory());
        factory.registerActorFactory(new IntegerDoubleMapJidFactory());
        factory.registerActorFactory(new IntegerBooleanMapJidFactory());

        factory.registerActorFactory(new LongStringMapJidFactory());
        factory.registerActorFactory(new LongBytesMapJidFactory());
        factory.registerActorFactory(new LongActorMapJidFactory());
        factory.registerActorFactory(new LongLongMapJidFactory());
        factory.registerActorFactory(new LongIntegerMapJidFactory());
        factory.registerActorFactory(new LongFloatMapJidFactory());
        factory.registerActorFactory(new LongDoubleMapJidFactory());
        factory.registerActorFactory(new LongBooleanMapJidFactory());
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
    }
}
