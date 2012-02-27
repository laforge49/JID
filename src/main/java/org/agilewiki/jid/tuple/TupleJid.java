package org.agilewiki.jid.tuple;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
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

    /**
     * Get the actor types.
     *
     * @throws Exception Any exceptions thrown during the initialization.
     */
    protected void fetchActorTypes() throws Exception {
        if (actorTypes == null)
            actorTypes = GetActorTypes.req.call(thisActor);
    }

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
                if (tuple != null)
                    return tuple[ndx].thisActor;
                ReadableBytes readableBytes = null;
                if (isSerialized()) {
                    readableBytes = serializedData.readable();
                    skipLen(readableBytes);
                }
                fetchActorTypes();
                tuple = new JID[actorTypes.length];
                int i = 0;
                len = 0;
                while (i < actorTypes.length) {
                    JID elementJid = createJid(i, internals);
                    if (readableBytes != null)
                        elementJid.load(readableBytes);
                    len += elementJid.getSerializedLength();
                    elementJid.containerJid = TupleJid.this;
                    tuple[i] = elementJid;
                    i += 1;
                }
                return tuple[ndx].thisActor;
            }
        });
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
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.INT_LENGTH + len;
    }
}
