package org.agilewiki.jid.tuple;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.requests.GetJIDComponent;

/**
 * Holds an array of actors.
 */
public class TupleJid extends JID {
    /**
     * An array of actor types, one for each element in the tuple.
     */
    protected String[] actorTypes;

    /**
     * A tuple of actors.
     */
    protected JID[] tuple;

    /**
     * The size of the serialized (exclusive of its length header).
     */
    protected int len;

    protected JID createJid(int i, Internals internals)
            throws Exception {
        String actorType = actorTypes[i];
        NewActor newActor = new NewActor(
                actorType,
                thisActor.getMailbox(),
                null,
                thisActor.getParent());
        JCActor elementActor = newActor.call(thisActor);
        return GetJIDComponent.req.call(internals, elementActor);
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(IGet.class.getName(), new SynchronousMethodBinding<IGet, JCActor>() {
            @Override
            public JCActor synchronousProcessRequest(Internals internals, IGet request)
                    throws Exception {
                int ndx = request.getI();
                return tuple[ndx].thisActor;
            }
        });
    }

    /**
     * Open is called when an actor becomes active by receiving a
     * non-initialization request--useful initialization like opening files.
     * Components are opened in dependency order, the root component being the last.
     *
     * @param internals The actor's internals.
     * @throws Exception Any exceptions thrown during the open.
     */
    @Override
    public void open(Internals internals) throws Exception {
        actorTypes = GetActorTypes.req.call(thisActor);
        ReadableBytes readableBytes = null;
        if (isSerialized()) {
            readableBytes = serializedData.readable();
            skipLen(readableBytes);
        }
        tuple = new JID[actorTypes.length];
        int i = 0;
        len = 0;
        while (i < actorTypes.length) {
            JID elementJid = createJid(i, internals);
            if (readableBytes != null) {
                elementJid.load(readableBytes);
            }
            len += elementJid.getSerializedLength();
            elementJid.containerJid = TupleJid.this;
            tuple[i] = elementJid;
            i += 1;
        }
    }

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
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH + len;
    }

    /**
     * Serialize the persistent data.
     *
     * @param appendableBytes The wrapped byte array into which the persistent data is to be serialized.
     */
    @Override
    protected void serialize(AppendableBytes appendableBytes) {
        saveLen(appendableBytes);
        int i = 0;
        while (i < actorTypes.length) {
            tuple[i].save(appendableBytes);
        }
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
        tuple = null;
        readableBytes.skip(len);
    }

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
}
