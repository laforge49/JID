package org.agilewiki.jid.scalar.jidjid;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jactor.bind.SynchronousMethodBinding;
import org.agilewiki.jactor.bind.VoidSynchronousMethodBinding;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.components.factory.NewActor;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.JID;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;
import org.agilewiki.jid.requests.GetJIDComponent;
import org.agilewiki.jid.requests.ResolvePathname;
import org.agilewiki.jid.scalar.GetValue;
import org.agilewiki.jid.scalar.MakeValue;
import org.agilewiki.jid.scalar.SetValue;

/**
 * A JID component that holds a JID actor.
 */
public class JidJid extends JID {
    public static final GetValue<JCActor> getValueReq = (GetValue<JCActor>) GetValue.req;

    public static final MakeValue makeValueReq(String actorType) {
        return new MakeValue(actorType);
    }

    public static final SetValue setValueReq(String actorType) {
        return new SetValue(actorType);
    }

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
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        super.bindery();

        thisActor.bind(GetValue.class.getName(),
                new SynchronousMethodBinding<GetValue<JCActor>, JCActor>() {
                    @Override
                    public JCActor synchronousProcessRequest(Internals internals, GetValue<JCActor> request)
                            throws Exception {
                        return getValue(internals);
                    }
                });

        thisActor.bind(MakeValue.class.getName(),
                new SynchronousMethodBinding<MakeValue, Boolean>() {
                    @Override
                    public Boolean synchronousProcessRequest(Internals internals, MakeValue request)
                            throws Exception {
                        return makeValue(internals, request);
                    }
                });

        thisActor.bind(SetValue.class.getName(),
                new VoidSynchronousMethodBinding<SetValue>() {
                    @Override
                    public void synchronousProcessRequest(Internals internals, SetValue request)
                            throws Exception {
                        setValue(internals, request);
                    }
                });
    }

    /**
     * Clear the ontent.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void clear(Internals internals) throws Exception {
        if (len == 0)
            return;
        int l = len;
        if (jidValue != null) {
            jidValue.containerJid = null;
            jidValue = null;
        }
        dser = true;
        change(internals, -l);
    }

    /**
     * Creates a jid actor.
     *
     * @param internals The actor's internals.
     * @param request   The MakeJIDValue request.
     * @return True if a new jid actor is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected void setValue(Internals internals, SetValue request)
            throws Exception {
        if (len > 0)
            clear(internals);
        String jidType = (String) request.getValue();
        NewActor na = new NewActor(jidType, thisActor.getMailbox(), null, thisActor.getParent());
        JCActor nja = na.call(thisActor);
        jidValue = (new GetJIDComponent()).call(internals, nja);
        jidValue.containerJid = this;
        int l = Util.stringLength(jidType) + jidValue.getSerializedLength();
        change(internals, l);
        dser = true;
    }

    /**
     * Creates a jid actor unless one is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeJIDValue request.
     * @return True if a new jid actor is created.
     * @throws Exception Any uncaught exception raised.
     */
    protected Boolean makeValue(Internals internals, MakeValue request)
            throws Exception {
        if (len > 0)
            return false;
        String jidType = (String) request.getValue();
        NewActor na = new NewActor(jidType, thisActor.getMailbox(), null, thisActor.getParent());
        JCActor nja = na.call(thisActor);
        jidValue = (new GetJIDComponent()).call(internals, nja);
        jidValue.containerJid = this;
        int l = Util.stringLength(jidType) + jidValue.getSerializedLength();
        change(internals, l);
        dser = true;
        return true;
    }

    /**
     * Returns the JID actor held by this component.
     *
     * @param internals The actor's internals.
     * @return The JID actor held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    protected JCActor getValue(Internals internals)
            throws Exception {
        if (dser)
            if (len == 0)
                return null;
            else
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
        JCActor nja = (new NewActor(
                actorType,
                thisActor.getMailbox(),
                null,
                thisActor.getParent())).call(thisActor);
        dser = true;
        jidValue = (new GetJIDComponent()).call(internals, nja);
        jidValue.load(readableBytes);
        jidValue.containerJid = this;
        return nja;
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
        if (pathname.equals("$")) {
            return getValue(internals);
        }
        if (pathname.startsWith("$/")) {
            JCActor jca = getValue(internals);
            ResolvePathname req = new ResolvePathname(pathname.substring(2));
            return req.call(internals, jca);
        }
        throw new IllegalArgumentException("pathname " + pathname + ";");
    }
}
