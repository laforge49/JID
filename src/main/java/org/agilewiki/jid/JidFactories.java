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
import org.agilewiki.jid.collection.flenc.TupleJid;
import org.agilewiki.jid.jidFactory.JidFactory;
import org.agilewiki.jid.scalar.flens.*;
import org.agilewiki.jid.scalar.vlens.BytesJid;
import org.agilewiki.jid.scalar.vlens.StringJid;
import org.agilewiki.jid.scalar.vlens.jidjid.JidJid;

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
     * The name of the JID component.
     */
    public final static String JID_ATYPE = "JIDa";

    /**
     * The name of the BooleanJid component.
     */
    public final static String BOOLEAN_JID_CTYPE = "BOOLEAN_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String INTEGER_JID_CTYPE = "INT_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String LONG_JID_CTYPE = "LONG_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String FLOAT_JID_CTYPE = "FLOAT_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String DOUBLE_JID_CTYPE = "DOUBLE_JIDc";

    /**
     * The name of the JidJid component.
     */
    public final static String JID_JID_CTYPE = "JID_JIDc";

    /**
     * The name of the JidJid component.
     */
    public final static String STRING_JID_CTYPE = "STRING_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String BYTES_JID_CTYPE = "BYTES_JIDc";

    /**
     * The name of the BooleanJid component.
     */
    public final static String TUPLE_JID_CTYPE = "TUPLE_JIDc";

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
        (new DefineActorType(JID_CTYPE, JidC.class)).call(thisActor);
        (new DefineActorType(JID_ATYPE, JidA.class)).call(thisActor);

        (new DefineActorType(BOOLEAN_JID_CTYPE, BooleanJid.class)).call(thisActor);
        (new DefineActorType(INTEGER_JID_CTYPE, IntegerJid.class)).call(thisActor);
        (new DefineActorType(LONG_JID_CTYPE, LongJid.class)).call(thisActor);
        (new DefineActorType(FLOAT_JID_CTYPE, FloatJid.class)).call(thisActor);
        (new DefineActorType(DOUBLE_JID_CTYPE, DoubleJid.class)).call(thisActor);

        (new DefineActorType(JID_JID_CTYPE, JidJid.class)).call(thisActor);
        (new DefineActorType(STRING_JID_CTYPE, StringJid.class)).call(thisActor);
        (new DefineActorType(BYTES_JID_CTYPE, BytesJid.class)).call(thisActor);

        (new DefineActorType(TUPLE_JID_CTYPE, TupleJid.class)).call(thisActor);
    }
}
