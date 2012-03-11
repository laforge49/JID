package org.agilewiki.jid;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jid.requests.*;

import java.util.Arrays;

/**
 * Base class for Incremental Deserialization Actors.
 */
public class JidA extends LiteActor implements Jid {
    /**
     * The JID actor which holds this actor.
     */
    private Jid containerJid;

    /**
     * Holds the serialized data.
     */
    protected byte[] serializedBytes;

    /**
     * The start of the serialized data.
     */
    protected int serializedOffset;

    /**
     * Create a JidA
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public JidA(final Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * Returns a new instance.
     *
     * @param m The mailbox.
     * @return The new instance.
     */
    public JidA newInstance(Mailbox m) {
        return new JidA(m);
    }

    /**
     * Returns a readable form of the serialized data.
     *
     * @return A ReadableBytes wrapper of the serialized data.
     */
    protected ReadableBytes readable() {
        return new ReadableBytes(serializedBytes, serializedOffset);
    }

    /**
     * Notification that the persistent data has changed.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the notification.
     */
    public void changed(int lengthChange)
            throws Exception {
        serializedBytes = null;
        serializedOffset = -1;
        if (containerJid == null)
            return;
        containerJid.change(lengthChange);
    }

    /**
     * Process a change in the persistent data.
     *
     * @param lengthChange The change in the size of the serialized data.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(int lengthChange) throws Exception {
        changed(lengthChange);
    }

    /**
     * Assign the container.
     *
     * @param containerJid The container, or null.
     */
    @Override
    public void setContainerJid(Jid containerJid) {
        this.containerJid = containerJid;
    }

    @Override
    public JidA thisActor() {
        return this;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return 0;
    }

    /**
     * Returns true when the persistent data is already serialized.
     *
     * @return True when the persistent data is already serialized.
     */
    final protected boolean isSerialized() {
        return serializedBytes != null;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    protected void serialize(AppendableBytes appendableBytes) {
    }

    /**
     * Saves the persistent data in a byte array.
     *
     * @param appendableBytes Holds the byte array and offset.
     */
    @Override
    public void save(AppendableBytes appendableBytes) {
        if (isSerialized()) {
            byte[] bs = appendableBytes.getBytes();
            int off = appendableBytes.getOffset();
            appendableBytes.writeBytes(serializedBytes, serializedOffset, getSerializedLength());
            serializedBytes = bs;
            serializedOffset = off;
        } else {
            serializedBytes = appendableBytes.getBytes();
            serializedOffset = appendableBytes.getOffset();
            serialize(appendableBytes);
        }
        if (serializedOffset + getSerializedLength() != appendableBytes.getOffset()) {
            System.err.println("\n" + getClass().getName());
            System.err.println("" + serializedOffset +
                    " + " + getSerializedLength() + " != " + appendableBytes.getOffset());
            throw new IllegalStateException();
        }
    }

    /**
     * Returns a byte array holding the serialized persistent data.
     *
     * @return The byte array holding the serialized persistent data.
     */
    final public byte[] getBytes() {
        byte[] bs = new byte[getSerializedLength()];
        AppendableBytes appendableBytes = new AppendableBytes(bs, 0);
        save(appendableBytes);
        return bs;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        serializedBytes = readableBytes.getBytes();
        serializedOffset = readableBytes.getOffset();
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param pathname A JID pathname.
     * @return A JID actor or null.
     * @throws Exception Any uncaught exception which occurred while processing the request.
     */
    @Override
    public JidA resolvePathname(String pathname) throws Exception {
        if (pathname != "")
            throw new IllegalArgumentException("pathname " + pathname);
        return this;
    }

    @Override
    public boolean equals(Object v) {
        if (v == null)
            return false;
        if (!v.getClass().equals(getClass()))
            return false;
        JidC jid = (JidC) v;
        if (jid.getSerializedLength() != getSerializedLength())
            return false;
        return Arrays.equals(jid.getBytes(), getBytes());
    }

    @Override
    public int hashCode() {
        return getBytes().hashCode();
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp)
            throws Exception {
        if (request instanceof ResolvePathname)
            rp.processResponse(resolvePathname(((ResolvePathname) request).getPathname()));
        else if (request instanceof CopyJID)
            rp.processResponse(copyJID(((CopyJID) request).getMailbox()));
        else if (request instanceof IsJidEqual)
            isJidEqual(((IsJidEqual) request).getJidActor(), rp);
        else throw new UnsupportedOperationException(request.getClass().getName());
    }

    public JidA copyJID(Mailbox m) {
        Mailbox mb = m;
        if (mb == null)
            mb = getMailbox();
        JidA jidA = newInstance(m);
        jidA.load(readable());
        return jidA;
    }

    public void isJidEqual(Actor actor, final RP rp)
            throws Exception {
        if (!(actor instanceof JidA)) {
            rp.processResponse(false);
            return;
        }
        final JidA jidA = (JidA) actor;
        send(jidA, GetSerializedLength.req, new RP<Integer>() {
            @Override
            public void processResponse(Integer response) throws Exception {
                if (response.intValue() != getSerializedLength()) {
                    rp.processResponse(false);
                    return;
                }
                send(jidA, GetBytes.req, new RP<byte[]>() {
                    @Override
                    public void processResponse(byte[] response) throws Exception {
                        boolean eq = Arrays.equals(response, getBytes());
                        rp.processResponse(eq);
                    }
                });
            }
        });
    }
}
