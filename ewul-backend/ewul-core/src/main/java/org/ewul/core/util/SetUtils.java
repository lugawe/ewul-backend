package org.ewul.core.util;

import java.util.*;

public final class SetUtils {

    private SetUtils() {
    }

    public static <T> SortedSet<T> sortedSet(Comparator<T> comparator, Collection<? extends T> values) {
        if (comparator == null) {
            throw new NullPointerException("comparator");
        }
        if (values == null || values.isEmpty()) {
            values = Collections.emptySet();
        }
        SortedSet<T> sortedSet = new TreeSet<>(comparator);
        sortedSet.addAll(values);
        return sortedSet;
    }

    @SafeVarargs
    public static <T> SortedSet<T> sortedSet(Comparator<T> comparator, T... values) {
        return sortedSet(comparator, Arrays.asList(values));
    }

    @SafeVarargs
    public static <T extends Comparable<T>> SortedSet<T> sortedSet(T... values) {
        return sortedSet(Comparator.naturalOrder(), Arrays.asList(values));
    }

}
