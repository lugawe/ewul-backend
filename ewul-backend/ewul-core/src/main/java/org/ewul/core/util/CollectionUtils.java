package org.ewul.core.util;

import java.util.Collection;
import java.util.Collections;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection != null ? collection : Collections.emptyList();
    }

}
