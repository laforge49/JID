package org.agilewiki.jid.tuple;

import org.agilewiki.jactor.bind.ConcurrentMethodBinding;
import org.agilewiki.jactor.bind.RequestReceiver;
import org.agilewiki.jactor.components.Component;
import org.agilewiki.jid.JidFactories;

/**
 * Defines (String, String) tuples.
 */
public class StringStringTuple extends Component {
    private static final String[] ats = new String[2];

    static {
        ats[0] = JidFactories.STRING_JID_TYPE;
        ats[1] = JidFactories.STRING_JID_TYPE;
    }

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        thisActor.bind(
                GetActorTypes.class.getName(),
                new ConcurrentMethodBinding<GetActorTypes, String[]>() {
                    @Override
                    public String[] concurrentProcessRequest(RequestReceiver requestReceiver,
                                                             GetActorTypes request)
                            throws Exception {
                        return ats;
                    }
                });
    }
}
