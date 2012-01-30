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
import org.agilewiki.jactor.ResponseProcessor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.MethodBinding;
import org.agilewiki.jactor.bind.SMBuilder;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.factory.Factory;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jactor.stateMachine.ActorFunc;
import org.agilewiki.jactor.stateMachine.ObjectFunc;
import org.agilewiki.jactor.stateMachine.StateMachine;
import org.agilewiki.jid.requests.GetJID;
import org.agilewiki.jid.requests.NewJID;

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
     * Initialize the component after all its includes have been processed.
     * The response must always be null;
     *
     * @param internals The JBActor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    @Override
    public void open(final Internals internals, final ResponseProcessor rp) throws Exception {
        super.open(internals, new ResponseProcessor() {
            @Override
            public void process(Object response) throws Exception {

                internals.bind(NewJID.class.getName(), new MethodBinding() {
                    @Override
                    public void processRequest(Internals internals, Object request, ResponseProcessor rp1)
                            throws Exception {
                        final NewJID newJID = (NewJID) request;
                        SMBuilder smBuilder = new SMBuilder(internals);
                        smBuilder._send(
                                internals.getThisActor(),
                                new NewActor(
                                        newJID.getActorType(),
                                        newJID.getMailbox(),
                                        newJID.getActorName(),
                                        newJID.getParent()), "actor");
                        smBuilder._send(
                                new ActorFunc() {
                                    @Override
                                    public Actor get(StateMachine sm) {
                                        return (Actor) sm.get("actor");
                                    }
                                },
                                new GetJID(),
                                "component");
                        smBuilder._return(new ObjectFunc() {
                            @Override
                            public Object get(StateMachine sm) {
                                JID jid = (JID) sm.get("component");
                                jid.putBytes(newJID.getBytes());
                                return sm.get("actor");
                            }
                        });
                        smBuilder.call(rp1);
                    }
                });

                rp.process(null);
            }
        });
    }
}
