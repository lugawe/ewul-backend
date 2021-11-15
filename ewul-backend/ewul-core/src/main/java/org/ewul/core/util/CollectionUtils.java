package org.ewul.core.util;

import java.util.*;

public final class CollectionUtils {

    private static class EmptyCollection<T> extends AbstractCollection<T> {

        private static final Lazy<EmptyCollection<?>> instance = Lazy.of(EmptyCollection::new);

        private EmptyCollection() {
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @SuppressWarnings("unchecked")
        public static <T> EmptyCollection<T> getInstance() {
            return (EmptyCollection<T>) instance.get();
        }

    }

    private CollectionUtils() {
    }

    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection != null ? collection : CollectionUtils.EmptyCollection.getInstance();
    }

    public static <T> List<T> asList(Collection<? extends T> items) {
        return new ArrayList<>(emptyIfNull(items));
    }

    @SafeVarargs
    public static <T> List<T> asList(T... items) {
        return asList(Arrays.asList(items));
    }

    public static <T> Set<T> asSet(Collection<? extends T> items) {
        return new LinkedHashSet<>(emptyIfNull(items));
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... items) {
        return asSet(Arrays.asList(items));
    }

}
