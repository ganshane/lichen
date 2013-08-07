package lichen.core.internal;

import lichen.core.services.Function;
import lichen.core.services.IterableLike;
import lichen.core.services.LichenCoreErrorCode;
import lichen.core.services.LichenException;

import java.util.Collection;
import java.util.Iterator;

/**
 * 针对{@link IterableLike}的实现
 * @author jcai
 */
public class IterableLikeImpl<A> implements IterableLike<A> {

    private final Collection<A> underlying;
    public IterableLikeImpl(Collection<A> collection){
        this.underlying  = collection;
    }
    private IterableLikeImpl(){
        this(null);
    }
    @Override
    public <B> IterableLike<B> map(final Function.Function1<A, B> function) {
        final Iterator<A> old= iterator();
        return new IterableLikeImpl<B>(){
            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    @Override
                    public boolean hasNext() {
                        return old.hasNext();
                    }

                    @Override
                    public B next() {
                        return function.apply(old.next());
                    }

                    @Override
                    public void remove() {
                        throw new LichenException(LichenCoreErrorCode.UNSUPPORT_REMOVE_ITERATOR);
                    }
                };
            }
        };
    }

    @Override
    public void foreach(Function.Function1<A, Void> function) {
        Iterator<A> it = iterator();
        while(it.hasNext()){
            function.apply(it.next());
        }
    }

    @Override
    public Iterator<A> iterator() {
        return underlying.iterator();
    }
}
