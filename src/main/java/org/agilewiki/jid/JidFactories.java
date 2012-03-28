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

import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.factory.DefineActorType;
import org.agilewiki.jactor.components.factory.RegisterActorFactory;
import org.agilewiki.jid.collection.flenc.TupleJidAFactory;
import org.agilewiki.jid.collection.flenc.TupleJidC;
import org.agilewiki.jid.collection.vlenc.ListJidAFactory;
import org.agilewiki.jid.collection.vlenc.ListJidC;
import org.agilewiki.jid.collection.vlenc.map.string.StringMapJidC;
import org.agilewiki.jid.collection.vlenc.map.string.StringStringJAMapJidC;
import org.agilewiki.jid.jidFactory.JidAFactory;
import org.agilewiki.jid.jidFactory.JidsFactory;
import org.agilewiki.jid.scalar.flens.bool.BooleanJidFactory;
import org.agilewiki.jid.scalar.flens.dbl.DoubleJidFactory;
import org.agilewiki.jid.scalar.flens.flt.FloatJidAFactory;
import org.agilewiki.jid.scalar.flens.integer.IntegerJidFactory;
import org.agilewiki.jid.scalar.flens.lng.LongJidFactory;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidAFactory;
import org.agilewiki.jid.scalar.vlens.actor.ActorJidC;
import org.agilewiki.jid.scalar.vlens.bytes.BytesJidAFactory;
import org.agilewiki.jid.scalar.vlens.bytes.BytesJidC;
import org.agilewiki.jid.scalar.vlens.string.StringJidAFactory;
import org.agilewiki.jid.scalar.vlens.string.StringJidC;

import java.util.ArrayList;

/**
 * <p>
 * Registers the JID factories.
 * </p>
 */
final public class JidFactories extends Component {
    /**
     * The name of the JID component.
     */
    public final static String JID_CTYPE = "JIDc";

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
     * The name of the JidJid component.
     */
    public final static String ACTOR_JID_CTYPE = "ACTOR_JIDc";

    /**
     * The name of the JidJid actor.
     */
    public final static String ACTOR_JID_ATYPE = "ACTOR_JIDa";

    /**
     * The name of the String component.
     */
    public final static String STRING_JID_CTYPE = "STRING_JIDc";

    /**
     * The name of the String actor.
     */
    public final static String STRING_JID_ATYPE = "STRING_JIDa";

    /**
     * The name of the BytesJid component.
     */
    public final static String BYTES_JID_CTYPE = "BYTES_JIDc";

    /**
     * The name of the BytesJid actor.
     */
    public final static String BYTES_JID_ATYPE = "BYTES_JIDa";

    /**
     * The name of the TupleJid component.
     */
    public final static String TUPLE_JID_CTYPE = "TUPLE_JIDc";

    /**
     * The name of the TupleJid actor.
     */
    public final static String TUPLE_JID_ATYPE = "TUPLE_JIDa";

    /**
     * The name of the ListJid component.
     */
    public final static String LIST_JID_CTYPE = "LIST_JIDc";

    /**
     * The name of the ListJid actor.
     */
    public final static String LIST_JID_ATYPE = "LIST_JIDa";

    /**
     * The name of the ListJid component.
     */
    public final static String STRING_MAP_JID_CTYPE = "STRING_MAP_JIDc";

    /**
     * The name of the ListJid component.
     */
    public final static String STRING_STRINGJA_MAP_JID_CTYPE = "STRING_STRINGa_MAP_JIDc";

    /**
     * Returns a list of Includes for inclusion in the actor.
     *
     * @return A list of classes for inclusion in the actor.
     */
    @Override
    public ArrayList<Include> includes() {
        ArrayList<Include> rv = new ArrayList<Include>();
        rv.add(new Include(JidsFactory.class));
        return rv;
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        (new DefineActorType(JID_CTYPE, JidC.class)).call(thisActor);
        (new RegisterActorFactory(new JidAFactory())).call(thisActor);

        (new RegisterActorFactory(new BooleanJidFactory())).call(thisActor);
        (new RegisterActorFactory(new IntegerJidFactory())).call(thisActor);
        (new RegisterActorFactory(new LongJidFactory())).call(thisActor);
        (new RegisterActorFactory(new FloatJidAFactory())).call(thisActor);
        (new RegisterActorFactory(new DoubleJidFactory())).call(thisActor);

        (new DefineActorType(ACTOR_JID_CTYPE, ActorJidC.class)).call(thisActor);
        (new RegisterActorFactory(new ActorJidAFactory())).call(thisActor);
        (new DefineActorType(STRING_JID_CTYPE, StringJidC.class)).call(thisActor);
        (new RegisterActorFactory(new StringJidAFactory())).call(thisActor);
        (new DefineActorType(BYTES_JID_CTYPE, BytesJidC.class)).call(thisActor);
        (new RegisterActorFactory(new BytesJidAFactory())).call(thisActor);

        (new DefineActorType(TUPLE_JID_CTYPE, TupleJidC.class)).call(thisActor);
        (new RegisterActorFactory(new TupleJidAFactory())).call(thisActor);
        (new DefineActorType(LIST_JID_CTYPE, ListJidC.class)).call(thisActor);
        (new RegisterActorFactory(new ListJidAFactory())).call(thisActor);

        (new DefineActorType(STRING_MAP_JID_CTYPE, StringMapJidC.class)).call(thisActor);
        (new DefineActorType(STRING_STRINGJA_MAP_JID_CTYPE, StringStringJAMapJidC.class)).call(thisActor);
    }
}
