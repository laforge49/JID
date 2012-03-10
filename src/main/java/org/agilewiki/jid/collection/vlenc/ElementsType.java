package org.agilewiki.jid.collection.vlenc;

import org.agilewiki.jactor.bind.ConcurrentMethodBinding;
import org.agilewiki.jactor.bind.RequestReceiver;
import org.agilewiki.jactor.components.Component;

/**
 * Base class for defining the elements type of a variable length collection.
 */
abstract public class ElementsType extends Component {

    /**
     * Returns an actor type.
     *
     * @return An actor type.
     */
    abstract protected String at();

    /**
     * Bind request classes.
     *
     * @throws Exception Any exceptions thrown while binding.
     */
    @Override
    public void bindery() throws Exception {
        thisActor.bind(
                GetElementsType.class.getName(),
                new ConcurrentMethodBinding<GetElementsType, String>() {
                    @Override
                    public String concurrentProcessRequest(RequestReceiver requestReceiver,
                                                           GetElementsType request)
                            throws Exception {
                        return at();
                    }
                });
    }
}
