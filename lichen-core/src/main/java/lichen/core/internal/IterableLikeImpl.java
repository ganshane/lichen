package lichen.core.internal;

import lichen.core.services.IterableLike;
import lichen.core.services.LichenCoreErrorCode;
import lichen.core.services.LichenException;
import lichen.core.services.Option;
import lichen.core.services.func.Function1;

import java.util.Collection;
import java.util.Iterator;

/**
 * 针对{@link IterableLike}的实现.
 *
 * @param <A> 集合中的元素类型
 * @author jcai
 */
public class IterableLikeImpl<A> implements IterableLike<A> {

    //underlying collection
    private final Collection<A> underlying;

    /**
     * 通过给定的集合来创建一个{@link IterableLike}对象
     * @param collection 待操作的集合对象
     */
    public IterableLikeImpl(final Collection<A> collection) {
        this.underlying = collection;
    }

    /**
     * 私有创建，仅仅供内部调用
     */
    private IterableLikeImpl() {
        this(null);
    }

    @Override
    public <B> IterableLike<B> map(final Function1<A, B> function) {
        final Iterator<A> old = iterator();
        return new IterableLikeImpl<B>() {
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
                        throw new LichenException(
                                LichenCoreErrorCode.UNSUPPORT_REMOVE_ITERATOR);
                    }
                };
            }
        };
    }

    @Override
    public void foreach(final Function1<A, Void> function) {
        Iterator<A> it = iterator();
        while (it.hasNext()) {
            function.apply(it.next());
        }
    }

    @Override
    public Option<A> first(final Function1<A, Boolean> function) {
        Iterator<A> it = iterator();
        while (it.hasNext()) {
            A obj = it.next();
            if (function.apply(obj)) {
                return Option.Some(obj);
            }
        }
        return Option.None();
    }

    @Override
    public Iterator<A> iterator() {
        return underlying.iterator();
    }
    
	@Override
	public boolean exists(Function1<A, Boolean> function) {
		Iterator<A> it = iterator();
		while (it.hasNext()) {
			A obj = it.next();
			if (function.apply(obj)) {
				return true;
			}
		}
		return false;
	}
}
