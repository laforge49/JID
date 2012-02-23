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

import org.agilewiki.jactor.bind.ConcurrentMethodBinding;
import org.agilewiki.jactor.bind.RequestReceiver;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.Factory;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.requests.NewJID;
import org.agilewiki.jid.requests.PutBytes;

import java.util.ArrayList;

/**
 * Creates a JID actor and loads its serialized data.
 */
public class JidFactory extends Component {
    /**
     * Returns a list of Includes for inclusion in the actor.
     *
     * @return A list of classes for inclusion in the actor.
     */
    @Override
    public ArrayList<Include> includes() {
        ArrayList<Include> rv = new ArrayList<Include>();
        rv.add(new Include(Factory.class));
        return rv;
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {

        thisActor.bind(NewJID.class.getName(), new ConcurrentMethodBinding<NewJID, JCActor>() {
            @Override
            public JCActor concurrentProcessRequest(RequestReceiver requestReceiver, NewJID request)
                    throws Exception {
                NewJID newJID = (NewJID) request;
                JCActor actor = (new NewActor(
                        newJID.getActorType(),
                        newJID.getMailbox(),
                        newJID.getActorName(),
                        newJID.getParent())).call(thisActor);
                (new PutBytes(newJID.getBytes())).call(actor);
                return actor;
            }
        });
    }
}
