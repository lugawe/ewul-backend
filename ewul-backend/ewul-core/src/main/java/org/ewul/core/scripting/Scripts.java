package org.ewul.core.scripting;

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
import java.util.Objects;
import java.util.function.Supplier;

public final class Scripts {

    private static final Logger log = LoggerFactory.getLogger(Scripts.class);

    private static class GroovyShellSupplier implements Supplier<GroovyShell> {

        private final ClassLoader classLoader;

        GroovyShellSupplier(ClassLoader classLoader) {
            this.classLoader = Objects.requireNonNull(classLoader);
        }

        private CompilerConfiguration compilerConfiguration() {
            CompilerConfiguration configuration = new CompilerConfiguration();
            configuration.setSourceEncoding("UTF-8");
            configuration.addCompilationCustomizers(new ASTTransformationCustomizer(CompileStatic.class));
            return configuration;
        }

        @Override
        public GroovyShell get() {
            return new GroovyShell(classLoader, compilerConfiguration());
        }

    }

    private static final Lazy<GroovyClassLoader> CLASS_LOADER
            = Lazy.of(() -> new GroovyClassLoader(ClassLoader.getSystemClassLoader()));

    private static final Lazy<GroovyShell> SHELL = Lazy.of(new GroovyShellSupplier(CLASS_LOADER.get()));

    private Scripts() {
    }

    private static GroovyShell shell() {
        return SHELL.get();
    }

    public static Script load(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        Script result = shell().parse(new InputStreamReader(inputStream));
        log.debug("new script loaded");
        return result;
    }

    public static Script load(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        return load(new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)));
    }

    public static Script load(File file) throws FileNotFoundException {
        if (file == null) {
            throw new NullPointerException("file");
        }
        return load(new FileInputStream(file));
    }

}
