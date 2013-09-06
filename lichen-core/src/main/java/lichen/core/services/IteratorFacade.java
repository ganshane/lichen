package lichen.core.services;

import lichen.core.internal.IterableLikeImpl;

/**
 * 针对Iterator的操作类.
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 */
public final class IteratorFacade {
    private IteratorFacade(){
    }
    public static <T> IterableLike<T> make(final T ... elements){
        return new IterableLikeImpl<T>(elements);
    }
}
