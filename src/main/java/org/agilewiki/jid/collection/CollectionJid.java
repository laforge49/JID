package org.agilewiki.jid.collection;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jid.*;

/**
 * A collection of JID actors.
 */
abstract public class CollectionJid extends JidC {

    /**
     * The size of the serialized data (exclusive of its length header).
     */
    protected int len;

    /**
     * Skip over the length at the beginning of the serialized data.
     *
     * @param readableBytes Holds the serialized data.
     */
    protected void skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }

    /**
     * Returns the size of the serialized data (exclusive of its length header).
     *
     * @param readableBytes Holds the serialized data.
     * @return The size of the serialized data (exclusive of its length header).
     */
    protected int loadLen(ReadableBytes readableBytes) {
        return readableBytes.readInt();
    }

    /**
     * Writes the size of the serialized data (exclusive of its length header).
     *
     * @param appendableBytes The object written to.
     */
    protected void saveLen(AppendableBytes appendableBytes) {
        appendableBytes.writeInt(len);
    }

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    abstract protected int size();

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    abstract protected Jid get(int i);

    /**
     * Process a change in the persistent data.
     *
     * @param internals    The actor's internals.
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(Internals internals, int lengthChange) throws Exception {
        len += lengthChange;
        super.change(internals, lengthChange);
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param internals The actor's internals.
     * @param pathname  A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public JCActor resolvePathname(Internals internals, String pathname)
            throws Exception {
        if (pathname.length() == 0) {
            return thisActor;
        }
        int s = pathname.indexOf("/");
        if (s == -1)
            s = pathname.length();
        if (s == 0)
            throw new IllegalArgumentException("pathname " + pathname);
        String ns = pathname.substring(0, s);
        int n = 0;
        try {
            n = Integer.parseInt(ns);
        } catch (Exception ex) {
            throw new IllegalArgumentException("pathname " + pathname);
        }
        if (n < 0 || n >= size())
            throw new IllegalArgumentException("pathname " + pathname);
        Jid jid = get(n);
        if (s == pathname.length())
            return jid.thisActor();
        return jid.resolvePathname(internals, pathname.substring(s + 1));
    }
}
