package com.akefirad.wiremock.compatiblizer;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.UrlMatchingStrategy;
import com.github.tomakehurst.wiremock.matching.UrlPathPattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getGetMethod;
import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getPostMethod;
import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getUrlPathEqualToMethod;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class ReflectiveWireMock {
    private static final Logger log = getLogger(ReflectiveWireMock.class);
    private static final Object STATIC = null;

    private final Method urlPathEqualToMethod;
    private final Method postMethod;
    private final Method getMethod;

    ReflectiveWireMock(Method urlPathEqualToMethod, Method postMethod, Method getMethod) {
        requireNonNull(urlPathEqualToMethod, "urlPathEqualToMethod cannot be null!");
        requireNonNull(postMethod, "postMethod cannot be null!");
        requireNonNull(getMethod, "getMethod cannot be null!");

        this.urlPathEqualToMethod = urlPathEqualToMethod;
        this.postMethod = postMethod;
        this.getMethod = getMethod;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T doUrlPathEqualTo(String url) {
        log.debug("Delegating 'urlPathEqualTo' method to {} by invoking it with {} as argument...", urlPathEqualToMethod, url);

        return (T) urlPathEqualToMethod.invoke(STATIC, url);
    }

    @SneakyThrows
    public MappingBuilder doPost(Object urlMatchingStrategy_OR_urlPattern) {
        log.debug("Delegating 'post' method to {} by invoking it with {} as argument...", postMethod, urlMatchingStrategy_OR_urlPattern);

        return (MappingBuilder) postMethod.invoke(STATIC, urlMatchingStrategy_OR_urlPattern);
    }

    public MappingBuilder doGet(Object urlMatchingStrategy_OR_urlPattern) {
        log.debug("Delegating 'get' method to {} by invoking it with {} as argument...", getMethod, urlMatchingStrategy_OR_urlPattern);

        return doHttpMethod(getMethod, urlMatchingStrategy_OR_urlPattern);
    }

    @SneakyThrows
    private MappingBuilder doHttpMethod(Method method, Object urlMatchingStrategy_OR_urlPattern) {
        return (MappingBuilder) method.invoke(STATIC, urlMatchingStrategy_OR_urlPattern);
    }

    static class WireMock158 extends ReflectiveWireMock {

        WireMock158() {
            super(getUrlPathEqualToMethod(String.class),
                    getPostMethod(UrlMatchingStrategy.class),
                    getGetMethod(UrlMatchingStrategy.class));
        }

        @Override
        @SuppressWarnings({"RedundantCast", "unchecked"})
        public <T> T doUrlPathEqualTo(String url) {
            return (T) (UrlMatchingStrategy) super.doUrlPathEqualTo(url);
        }

        @Override
        @SuppressWarnings("RedundantCast")
        public MappingBuilder doPost(Object urlMatchingStrategy) {
            return super.doPost((UrlMatchingStrategy) urlMatchingStrategy);
        }

        @Override
        @SuppressWarnings("RedundantCast")
        public MappingBuilder doGet(Object urlMatchingStrategy) {
            return super.doGet((UrlMatchingStrategy) urlMatchingStrategy);
        }

    }

    static class WireMock251 extends ReflectiveWireMock {

        WireMock251() {
            super(getUrlPathEqualToMethod(String.class),
                    getPostMethod(UrlPattern.class),
                    getGetMethod(UrlPattern.class));
        }

        @Override
        @SuppressWarnings({"RedundantCast", "unchecked"})
        public <T> T doUrlPathEqualTo(String url) {
            return (T) (UrlPathPattern) super.doUrlPathEqualTo(url);
        }

        @Override
        @SuppressWarnings("RedundantCast")
        public MappingBuilder doPost(Object urlPattern) {
            return super.doPost((UrlPattern) urlPattern);
        }

        @Override
        @SuppressWarnings("RedundantCast")
        public MappingBuilder doGet(Object urlPattern) {
            return super.doGet((UrlPattern) urlPattern);
        }

    }
}
