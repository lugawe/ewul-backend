package org.ewul.core.util;

import com.google.common.collect.Maps;

import java.util.*;

public final class MapUtils {

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

    public static <V> Map<String, String> toStringValueMap(Map<String, V> map) {
        if (map == null) {
            throw new NullPointerException("param map");
        }
        return Maps.transformEntries(map, (key, value) -> Objects.requireNonNull(value).toString());
    }

}
