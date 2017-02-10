package com.akefirad.wiremock.compatiblizer;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getWithHeaderMethod;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class ReflectiveResponseDefinitionBuilder {
    private static final Logger log = getLogger(ReflectiveResponseDefinitionBuilder.class);

    private final ResponseDefinitionBuilder builder;

    public ReflectiveResponseDefinitionBuilder() {
        log.debug("Constructing a new ReflectiveResponseDefinitionBuilder object: {}", this);
        this.builder = new ResponseDefinitionBuilder();
    }

    public ReflectiveResponseDefinitionBuilder withStatus(int status) {
        builder.withStatus(status);
        return this;
    }

    @SneakyThrows
    public ReflectiveResponseDefinitionBuilder withHeader(String key, String value) {
        Method method = withHeaderMethod();
        Object argument = withHeaderArgument(value);

        log.debug("Delegating 'withHeader' method to {} by invoking it on {} with {} as argument...", method, builder, argument);

        method.invoke(builder, key, argument);
        return this;
    }

    public ReflectiveResponseDefinitionBuilder withBody(String body) {
        builder.withBody(body);
        return this;
    }

    public ResponseDefinitionBuilder get() {
        return builder;
    }

    abstract Method withHeaderMethod();

    abstract Object withHeaderArgument(String value);

    static class ResponseDefinitionBuilder158 extends ReflectiveResponseDefinitionBuilder {
        private static final Method WITH_HEADER_METHOD = getWithHeaderMethod(String.class);

        @Override
        Method withHeaderMethod() {
            return WITH_HEADER_METHOD;
        }

        @Override
        Object withHeaderArgument(String value) {
            return value;
        }
    }

    static class ResponseDefinitionBuilder251 extends ReflectiveResponseDefinitionBuilder {
        private static final Method WITH_HEADER_METHOD = getWithHeaderMethod(String[].class);

        @Override
        Method withHeaderMethod() {
            return WITH_HEADER_METHOD;
        }

        @Override
        Object withHeaderArgument(String value) {
            return new String[]{value};
        }
    }

}
