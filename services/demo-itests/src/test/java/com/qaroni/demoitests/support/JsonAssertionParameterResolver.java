package com.qaroni.demoitests.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.nio.file.Path;

public class JsonAssertionParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(JsonAssertion.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        JsonAssertions JsonAssertions =
                AnnotationSupport.findAnnotation(extensionContext.getRequiredTestClass(), JsonAssertions.class)
                        .orElseThrow();

        return build(JsonAssertions, extensionContext);
    }

    public JsonAssertion build(JsonAssertions JsonAssertions, ExtensionContext extensionContext) {
        ExtensionContext.Store store = extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL);

        return store.getOrComputeIfAbsent(JsonAssertions, this::build, JsonAssertion.class);
    }

    private JsonAssertion build(JsonAssertions JsonAssertions) {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

        return new JsonAssertion(objectMapper, Path.of(JsonAssertions.basePath()));
    }
}
