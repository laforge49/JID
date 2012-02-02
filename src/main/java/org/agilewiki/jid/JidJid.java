package org.agilewiki.jid;

import org.agilewiki.jactor.ResponseProcessor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;

/**
 * A JID component that holds a JID actor.
 */
public class JidJid extends JID {
    /**
     * True when deserialized.
     */
    protected boolean dser = true;

    /**
     * Holds the JID actor value, or null.
     */
    protected JID jidValue;

    /**
     * The size of the serialized (exclusive of its length header). 
     */
    protected int len = 0;
    
    /**
     * Initialize the component after all its includes have been processed.
     * The response must always be null;
     *
     * @param internals The JBActor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    @Override
    public void open(Internals internals, final ResponseProcessor rp) throws Exception {
        super.open(internals, new ResponseProcessor() {
            @Override
            public void process(Object response) throws Exception {
                rp.process(null);
            }
        });
    }

    /**
     * Returns true when the JID has been deserialized.
     *
     * @return True when the JID has been deserialized.
     */
    @Override
    protected boolean isDeserialized() {
        return dser;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH + len;
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
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        len = loadLen(readableBytes);
        jidValue = null;
        if (len > 0) {
            readableBytes.skip(len);
            dser = false;
        } else dser = true;
    }

    /**
     * Process a change in the persistent data.
     *
     * @param receiverInternals The internals of the receiving actor.
     * @param lengthChange      The change in the size of the serialized data.
     * @param rp                The response processor.
     * @throws Exception Any uncaught exception which occurred while processing the change.
     */
    @Override
    public void change(Internals receiverInternals, int lengthChange, ResponseProcessor rp) throws Exception {
        len += lengthChange;
        super.change(receiverInternals, lengthChange, rp);
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        if (!dser)
            throw new IllegalStateException();
        saveLen(appendableBytes);
        if (len == 0)
            return;
        String actorType = jidValue.thisActor.getActorType();
        appendableBytes.writeString(actorType);
        jidValue.save(appendableBytes);
    }

    /**
     * Skip over the length at the beginning of the serialized data.
     * 
     * @param readableBytes Holds the serialized data.
     */
    protected void skipLen(ReadableBytes readableBytes) {
        readableBytes.skip(Util.INT_LENGTH);
    }
    
    protected JCActor getJidValue() {
        if (dser)
            return jidValue.thisActor;
        if (!isSerialized()) 
            throw new IllegalStateException();
        ReadableBytes readableBytes = serializedData.readable();
        skipLen(readableBytes);
        if (len == 0) {
            dser = true;
            return null;
        }
        String actorType = readableBytes.readString();
        NewActor nj = new NewActor(
                actorType, 
                thisActor.getMailbox(), 
                null,
                thisActor.getParent());
        //todo
        return null;
    }

    /**
     * Resolves a JID pathname, returning a JID actor or null.
     *
     * @param internals The internals of the actor.
     * @param pathname A JID pathname.
     * @throws Exception        Any uncaught exception which occurred while processing the request.
     */
    @Override
    public void resolvePathname(Internals internals, String pathname, ResponseProcessor rp)
            throws Exception {
        if (pathname.length() == 0) {
            rp.process(null);
            return;
        }
        if (pathname.startsWith("/"))
            throw new IllegalArgumentException("pathname " + pathname);
        //todo
    }
}
