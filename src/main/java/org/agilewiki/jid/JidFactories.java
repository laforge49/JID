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
import org.agilewiki.jid.collection.vlenc.ListJidFactory;
import org.agilewiki.jid.collection.vlenc.map.string.StringMapJidFactory;
import org.agilewiki.jid.collection.vlenc.map.string.StringStringMapJidFactory;
import org.agilewiki.jid.jidFactory.JidFactory;
import org.agilewiki.jid.jidFactory.JidsFactoryFactory;
import org.agilewiki.jid.jidFactory.NewJID;
import org.agilewiki.jid.scalar.flens.bool.BooleanJidFactory;
import org.agilewiki.jid.scalar.flens.dbl.DoubleJidFactory;
import org.agilewiki.jid.scalar.flens.flt.FloatJidAFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.flens.lng.LongJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidFactory;
import org.agilewiki.jid.scalar.vlens.bytes.BytesJidFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidFactory;

/**
 * <p>
 * Registers the JID factories.
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
     * The name of the ListJid actor.
     */
    public final static String LIST_JID_TYPE = "LIST_JID";

    /**
     * The name of the ListJid component.
     */
    public final static String STRING_MAP_JID_TYPE = "STRING_MAP_JID";

    /**
     * The name of the ListJid component.
     */
    public final static String STRING_STRING_MAP_JID_TYPE = "STRING_STRING_MAP_JID";

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

        factory.registerActorFactory(new JidFactory());

        factory.registerActorFactory(new BooleanJidFactory());
        factory.registerActorFactory(new IntegerJidFactory());
        factory.registerActorFactory(new LongJidFactory());
        factory.registerActorFactory(new FloatJidAFactory());
        factory.registerActorFactory(new DoubleJidFactory());

        factory.registerActorFactory(new ActorJidFactory());
        factory.registerActorFactory(new StringJidFactory());
        factory.registerActorFactory(new BytesJidFactory());

        factory.registerActorFactory(new TupleJidFactory());
        factory.registerActorFactory(new ListJidFactory());

        factory.registerActorFactory(new StringMapJidFactory());
        factory.registerActorFactory(new StringStringMapJidFactory());
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
