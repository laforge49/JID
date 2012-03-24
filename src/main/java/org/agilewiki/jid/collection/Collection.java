package org.agilewiki.jid.collection;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jid.Jid;

/**
 * A collection of JID actors.
 */
public interface Collection {

    /**
     * Returns the size of the collection.
     *
     * @return The size of the collection.
     */
    public int size()
            throws Exception;

    /**
     * Returns the ith JID component.
     *
     * @param i The index of the element of interest.
     * @return The ith JID component.
     */
    public Jid iGetJid(int i)
            throws Exception;

    /**
     * Returns the selected element.
     *
     * @param ndx Selects the element.
     * @return The actor held by the selected element.
     */
    public Actor iGet(int ndx)
            throws Exception;

    /**
     * Creates a JID actor and loads its serialized data.
     *
     * @param i     The index of the desired element.
     * @param bytes Holds the serialized data.
     * @throws Exception Any exceptions thrown while processing the request.
     */
    public void iSetBytes(int i, byte[] bytes)
            throws Exception;
}
