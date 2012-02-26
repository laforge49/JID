package org.agilewiki.jid.scalar;

import org.agilewiki.jactor.bind.Internals;

/**
 * A JID component that holds a boolean.
 */
public class BooleanJid extends ScalarJid<Boolean> {
    private Boolean value;

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
    }

    /**
     * Assign a value unless one is already present.
     *
     * @param internals The actor's internals.
     * @param request   The MakeValue request.
     * @return True if a new value is created.
     * @throws Exception Any uncaught exception raised.
     */
    @Override
    protected Boolean makeValue(Internals internals, MakeValue request) throws Exception {
        Boolean v = (Boolean) request.getValue();
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        return value;
    }
}
