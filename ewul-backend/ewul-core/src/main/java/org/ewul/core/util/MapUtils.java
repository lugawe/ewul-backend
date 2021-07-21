package org.ewul.core.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

public final class MapUtils {

    private MapUtils() {
    }

    public static <V> Map<String, String> toStringValueMap(Map<String, V> map) {
        if (map == null) {
            throw new NullPointerException("param map");
        }
        return Maps.transformEntries(map, (key, value) -> Objects.requireNonNull(value).toString());
    }

}
