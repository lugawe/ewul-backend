package org.ewul.core.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.transform.CompileStatic;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.ewul.core.util.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class Scripts {

    private static final Logger log = LoggerFactory.getLogger(Scripts.class);

    private static class GroovyShellSupplier implements Supplier<GroovyShell> {

        final ClassLoader classLoader;
        final Binding binding;
        final Lazy<GroovyShell> groovyShell;

        GroovyShellSupplier(ClassLoader classLoader, Binding binding) {
            this.classLoader = Objects.requireNonNull(classLoader, "classLoader");
            this.binding = Objects.requireNonNull(binding, "binding");
            this.groovyShell = Lazy.of(() -> new GroovyShell(classLoader, binding, compilerConfiguration()));
        }

        CompilerConfiguration compilerConfiguration() {
            CompilerConfiguration configuration = new CompilerConfiguration();
            configuration.setTargetBytecode("1.8");
            configuration.setSourceEncoding("UTF-8");
            configuration.addCompilationCustomizers(new ASTTransformationCustomizer(CompileStatic.class));
            return configuration;
        }

        @Override
        public final GroovyShell get() {
            return groovyShell.get();
        }

    }

    private static final Lazy<GroovyClassLoader> CLASS_LOADER
            = Lazy.of(() -> new GroovyClassLoader(ClassLoader.getSystemClassLoader()));

    private Scripts() {
    }

    private static GroovyShell shell(Binding binding) {
        return new GroovyShellSupplier(CLASS_LOADER.get(), binding).get();
    }

    private static GroovyShell shell(Map<?, ?> binding) {
        return shell(new Binding(new LinkedHashMap<>(binding)));
    }

    public static Script load(InputStream inputStream, Map<?, ?> binding) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        Script result = shell(binding).parse(new InputStreamReader(inputStream));
        log.debug("new script loaded");
        return result;
    }

    public static Script load(InputStream inputStream) {
        return load(inputStream, Collections.emptyMap());
    }

    public static Script load(String value, Map<?, ?> binding) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        return load(new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)), binding);
    }

    public static Script load(String value) {
        return load(value, Collections.emptyMap());
    }

    public static Script load(File file, Map<?, ?> binding) throws FileNotFoundException {
        if (file == null) {
            throw new NullPointerException("file");
        }
        return load(new FileInputStream(file), binding);
    }

    public static Script load(File file) throws FileNotFoundException {
        return load(file, Collections.emptyMap());
    }

}
