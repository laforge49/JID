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
package org.agilewiki.jid.jidFactory;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.bind.ConcurrentMethodBinding;
import org.agilewiki.jactor.bind.RequestReceiver;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.factory.Factory;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.GetActorFactory;
import org.agilewiki.jactor.factory.NewActor;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid._Jid;

import java.util.ArrayList;

/**
 * A component to create JID actors and load their serialized data.
 */
final public class JidsFactory extends Component {
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

        thisActor.bind(GetJidFactory.class.getName(), new ConcurrentMethodBinding<GetJidFactory, _JidFactory>() {
            @Override
            public _JidFactory concurrentProcessRequest(RequestReceiver requestReceiver, GetJidFactory request) throws Exception {
                ActorFactory actorFactory = (new GetActorFactory(request.getActorType())).call(thisActor);
                return (_JidFactory) actorFactory;
            }
        });

        thisActor.bind(NewJID.class.getName(), new ConcurrentMethodBinding<NewJID, _Jid>() {
            @Override
            public _Jid concurrentProcessRequest(RequestReceiver requestReceiver, NewJID request)
                    throws Exception {
                _Jid container = request.getContainer();
                ReadableBytes readableBytes = request.getReadableBytes();
                Actor actor = (new NewActor(
                        request.getActorType(),
                        request.getMailbox(),
                        request.getParent())).call(thisActor);
                _Jid jid = (_Jid) actor;
                if (readableBytes != null)
                    jid.load(readableBytes);
                if (container != null)
                    jid.setContainerJid(container);
                return jid;
            }
        });
    }
}
