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
package org.agilewiki.jid.requests;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.bind.JBActor;
import org.agilewiki.jactor.bind.SynchronousRequest;
import org.agilewiki.jactor.components.JCActor;

/**
 * Returns a copy of the JID.
 */
final public class CopyJID extends SynchronousRequest<JCActor> {
    /**
     * A mailbox which may be shared with other actors.
     */
    private Mailbox mailbox;

    /**
     * The parent actor to which unrecognized requests are forwarded.
     */
    private JBActor parent;

    /**
     * Create a CopyJID request.
     */
    public CopyJID() {
    }

    /**
     * Create a CopyJID request.
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public CopyJID(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Create a CopyJID request.
     *
     * @param mailbox A mailbox which may be shared with other actors.
     * @param parent  The parent actor to which unrecognized requests are forwarded.
     */
    public CopyJID(Mailbox mailbox, JBActor parent) {
        this.mailbox = mailbox;
        this.parent = parent;
    }

    /**
     * Returns a mailbox which may be shared with other actors.
     *
     * @return A mailbox which may be shared with other actors, or null.
     */
    public Mailbox getMailbox() {
        return mailbox;
    }

    /**
     * Returns the parent actor to which unrecognized requests are forwarded.
     *
     * @return The parent actor to which unrecognized requests are forwarded, or null.
     */
    public JBActor getParent() {
        return parent;
    }
}
