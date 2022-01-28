package org.ewul.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Supplier;

public final class NullUtils {

    private static final Logger log = LoggerFactory.getLogger(NullUtils.class);

    private NullUtils() {
    }

    public static <T> T safeGet(Supplier<T> supplier, T defaultValue) {
        try {
            if (supplier == null) {
                throw new NullPointerException("supplier");
            }
            return Objects.requireNonNull(supplier.get(), "supplier returned null");
        } catch (Exception e) {
            log.debug("safeGet", e);
            return defaultValue;
        }
    }

}
