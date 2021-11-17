package org.ewul.core.scripting;

import groovy.lang.Script;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ScriptsTest {

    private static final String val1 = "return 1 + 41";
    private static final String val2 = "return 'ä'";
    private static final String invalid_val = "boo";

    @Test
    void test() {

        Script s1 = Scripts.load(val1);
        Object o1 = s1.run();
        assertEquals(42, o1);

        Script s2 = Scripts.load(val2);
        Object o2 = s2.run();
        assertEquals("ä", o2);

        assertThrows(MultipleCompilationErrorsException.class, () -> Scripts.load(invalid_val));

    }

}
