package com.akefirad.wiremock.compatiblizer;

import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getWillReturnMethod;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class ReflectiveMappingBuilder {
    private static final Logger log = getLogger(ReflectiveMappingBuilder.class);

    private final Object mappingBuilder;
    private final Method willReturnMethod;

    public ReflectiveMappingBuilder(Object mappingBuilder) {
        requireNonNull(mappingBuilder, "mappingBuilder cannot be null!");

        this.mappingBuilder = mappingBuilder;
        this.willReturnMethod = getWillReturnMethod();
    }

    @SneakyThrows
    public ReflectiveMappingBuilder willReturn(ReflectiveResponseDefinitionBuilder responseDefinitionBuilder) {
        log.debug("Delegating 'willReturn' method to {} by invoking it on {} with {} as argument...",
                willReturnMethod, mappingBuilder, responseDefinitionBuilder.get());

        willReturnMethod.invoke(mappingBuilder, responseDefinitionBuilder.get());
        return this;
    }

    public Object get() {
        return mappingBuilder;
    }
}
