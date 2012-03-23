package org.agilewiki.jid.collection.vlenc.map;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.bind.Internals;
import org.agilewiki.jid.ComparableKey;
import org.agilewiki.jid.Jid;
import org.agilewiki.jid.collection.IGet;
import org.agilewiki.jid.collection.vlenc.ListJidC;

/**
 * Holds a map.
 */
public class MapJidC<KEY_TYPE extends Comparable> extends ListJidC {
    /**
     * Locate the tuple with a matching first element.
     *
     * @param key The key which matches to the tuple's first element.
     * @return The index or - (insertion point + 1).
     */
    protected int search(KEY_TYPE key) throws Exception {
        int low = 0;
        int high = size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            ComparableKey<KEY_TYPE> midVal = (ComparableKey<KEY_TYPE>) list.get(mid);
            int c = midVal.compareKeyTo(key);
            if (c < 0)
                low = mid + 1;
            else if (c > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -(low + 1);
    }

    /**
     * Add a tuple to the map unless there is a tuple with a matching first element.
     *
     * @param key Used to match the first element of the tuples.
     * @return True if a new tuple was created.
     */
    protected Boolean kMake(Internals sourceInternals, KEY_TYPE key)
            throws Exception {
        int i = search(key);
        if (i > -1)
            return false;
        i = -i - 1;
        iAdd(i);
        Jid t = get(i);
        Actor ta = t.thisActor();
        Actor e0a = (new IGet(0)).call(sourceInternals, ta);
        //todo set the key
        return true;
    }
}
