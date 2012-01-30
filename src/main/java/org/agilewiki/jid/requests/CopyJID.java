package org.agilewiki.jid.requests;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;

/**
 * Returns a copy of the JID.
 */
public class CopyJID {
    /**
     * A mailbox which may be shared with other actors.
     */
    private Mailbox mailbox;

    /**
     * The parent actor to which unrecognized requests are forwarded.
     */
    private Actor parent;

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
    public CopyJID(Mailbox mailbox, Actor parent) {
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
    public Actor getParent() {
        return parent;
    }
}
