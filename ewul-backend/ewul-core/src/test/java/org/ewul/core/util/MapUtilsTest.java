package org.ewul.core.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilsTest {

    static final String PREFIX = "My name is ";

    static class Holder {

        private final String name;

        public Holder(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return PREFIX + name;
        }

    }

    @Test
    void toStringValueMap_test() {

        Map<Integer, Holder> map = new HashMap<>();
        map.put(42, new Holder("yeet"));
        map.put(69, new Holder("foobar"));

        Map<Integer, String> transformed = MapUtils.toStringValueMap(map);
        transformed.put(1, "2");

        assertTrue(transformed.containsKey(1));
        assertTrue(transformed.containsKey(42));
        assertTrue(transformed.containsKey(69));

        assertEquals(transformed.get(1), "2");
        assertEquals(transformed.get(42), PREFIX + "yeet");
        assertEquals(transformed.get(69), PREFIX + "foobar");

    }

}
