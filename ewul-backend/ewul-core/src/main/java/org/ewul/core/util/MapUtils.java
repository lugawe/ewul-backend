package org.ewul.core.util;

import com.google.common.collect.Maps;

import java.util.*;

public final class MapUtils {

    static class ToStringTransformer<K, V> implements Maps.EntryTransformer<K, V, String> {

        static final Lazy<ToStringTransformer<?, ?>> instance = Lazy.of(ToStringTransformer::new);

        ToStringTransformer() {
        }

        @Override
        public String transformEntry(K key, V value) {
            if (value == null) {
                return null;
            } else if (value instanceof String) {
                return (String) value;
            } else {
                return value.toString();
            }
        }

        @SuppressWarnings("unchecked")
        static <K, V> ToStringTransformer<K, V> getInstance() {
            return (ToStringTransformer<K, V>) instance.get();
        }

    }

    private MapUtils() {
    }

    public static <K, V> SortedMap<K, V> sortedMap(Comparator<K> comparator, Map<? extends K, ? extends V> map) {
        if (comparator == null) {
            throw new NullPointerException("param comparator");
        }
        if (map == null || map.isEmpty()) {
            map = Collections.emptyMap();
        }
        TreeMap<K, V> result = new TreeMap<>(comparator);
        result.putAll(map);
        return result;
    }

    public static <K, V> SortedMap<K, V> sortedMap(Comparator<K> comparator) {
        return sortedMap(comparator, null);
    }

    public static <K, V> Map<K, String> toStringValueMap(Map<K, V> map) {
        if (map == null) {
            throw new NullPointerException("param map");
        }
        Map<K, String> view = Maps.transformEntries(map, ToStringTransformer.getInstance());
        return new LinkedHashMap<>(view);
    }

}
