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
import org.agilewiki.jid.jidFactory.JidFactory;
import org.agilewiki.jid.scalar.flen.*;
import org.agilewiki.jid.scalar.vlen.BytesJid;
import org.agilewiki.jid.scalar.vlen.StringJid;
import org.agilewiki.jid.scalar.vlen.jidjid.JidJid;
import org.agilewiki.jid.tuple.TupleJid;

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
    public final static String JID_TYPE = "JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String BOOLEAN_JID_TYPE = "BOOLEAN_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String INTEGER_JID_TYPE = "INT_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String LONG_JID_TYPE = "LONG_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String FLOAT_JID_TYPE = "FLOAT_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String DOUBLE_JID_TYPE = "DOUBLE_JID";

    /**
     * The name of the JidJid component.
     */
    public final static String JID_JID_TYPE = "JID_JID";

    /**
     * The name of the JidJid component.
     */
    public final static String STRING_JID_TYPE = "STRING_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String BYTES_JID_TYPE = "BYTES_JID";

    /**
     * The name of the BooleanJid component.
     */
    public final static String TUPLE_JID_TYPE = "TUPLE_JID";

    /**
     * Returns a list of Includes for inclusion in the actor.
     *
     * @return A list of classes for inclusion in the actor.
     */
    @Override
    public ArrayList<Include> includes() {
        ArrayList<Include> rv = new ArrayList<Include>();
        rv.add(new Include(JidFactory.class));
        return rv;
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        (new DefineActorType(JID_TYPE, JID.class)).call(thisActor);

        (new DefineActorType(BOOLEAN_JID_TYPE, BooleanJid.class)).call(thisActor);
        (new DefineActorType(INTEGER_JID_TYPE, IntegerJid.class)).call(thisActor);
        (new DefineActorType(LONG_JID_TYPE, LongJid.class)).call(thisActor);
        (new DefineActorType(FLOAT_JID_TYPE, FloatJid.class)).call(thisActor);
        (new DefineActorType(DOUBLE_JID_TYPE, DoubleJid.class)).call(thisActor);

        (new DefineActorType(JID_JID_TYPE, JidJid.class)).call(thisActor);
        (new DefineActorType(STRING_JID_TYPE, StringJid.class)).call(thisActor);
        (new DefineActorType(BYTES_JID_TYPE, BytesJid.class)).call(thisActor);

        (new DefineActorType(TUPLE_JID_TYPE, TupleJid.class)).call(thisActor);
    }
}
