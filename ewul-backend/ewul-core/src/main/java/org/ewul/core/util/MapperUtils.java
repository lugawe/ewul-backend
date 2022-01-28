package org.ewul.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

public final class MapperUtils {

    private static final Lazy<ObjectMapper> mapper = Lazy.of(ObjectMapper::new);

    private static final TypeReference<Map<String, Object>> mapTypeReference
            = new TypeReference<Map<String, Object>>() {};

    private MapperUtils() {
    }

    public static <T extends Serializable> Map<String, Object> toMap(T value) {

        if (value == null) {
            throw new NullPointerException("value");
        }

        return mapper.get().convertValue(value, mapTypeReference);
    }

    public static <T extends Serializable> T fromMap(Map<String, Object> map, Class<T> tClass) {

        if (map == null) {
            throw new NullPointerException("map");
        }
        if (tClass == null) {
            throw new NullPointerException("tClass");
        }

        return mapper.get().convertValue(map, tClass);
    }

}
