package org.agilewiki.jid.scalar;

import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jid.AppendableBytes;
import org.agilewiki.jid.ReadableBytes;
import org.agilewiki.jid.Util;

/**
 * A JID component that holds a boolean.
 */
public class BooleanJid extends ScalarJid<Boolean> {
    /**
     * The GetValue request.
     */
    public static final GetValue<Boolean> getValueReq = (GetValue<Boolean>) GetValue.req;

    /**
     * Returns the MakeValue request.
     *
     * @param value The value.
     * @return The MakeValue request.
     */
    public static final MakeValue makeValueReq(Boolean value) {
        return new MakeValue(value);
    }

    /**
     * Returns the SetValue request.
     *
     * @param value The value.
     * @return The SetValue request.
     */
    public static final SetValue setValueReq(Boolean value) {
        return new SetValue(value);
    }

    /**
     * The value.
     */
    private Boolean value;

    /**
     * Clear the content.
     *
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void clear(Internals internals) throws Exception {
        if (!value.booleanValue())
            return;
        serializedData = null;
        value = false;
        dser = true;
        change(internals, 0);
    }

    /**
     * Assign a value.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected void setValue(Internals internals, SetValue request) throws Exception {
        Boolean v = (Boolean) request.getValue();
        if (value.equals(v))
            return;
        value = v;
        serializedData = null;
        dser = true;
        change(internals, 0);
    }

    /**
     * Assign a value unless already set to true.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(Internals internals, MakeValue request) throws Exception {
        if (value.booleanValue())
            return false;
        Boolean v = (Boolean) request.getValue();
        if (value.equals(v))
            return true;
        value = v;
        serializedData = null;
        dser = true;
        change(internals, 0);
        return true;
    }

    /**
     * Returns the value held by this component.
     *
     * @param internals The actor's internals.
     * @return The value held by this component.
     * @throws Exception Any uncaught exception raised during deserialization.
     */
    @Override
    protected Boolean getValue(Internals internals) throws Exception {
        if (dser)
            return value;
        if (!isSerialized())
            throw new IllegalStateException();
        ReadableBytes readableBytes = serializedData.readable();
        value = readableBytes.readBoolean();
        return value;
    }

    /**
     * Returns the number of bytes needed to serialize the persistent data.
     *
     * @return The minimum size of the byte array needed to serialize the persistent data.
     */
    @Override
    public int getSerializedLength() {
        return Util.BOOLEAN_LENGTH;
    }

    /**
     * Load the serialized data into the JID.
     *
     * @param readableBytes Holds the serialized data.
     */
    @Override
    public void load(ReadableBytes readableBytes) {
        super.load(readableBytes);
        value = false;
        dser = false;
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
        appendableBytes.writeBoolean(value);
    }
}
