package com.akefirad.wiremock.compatiblizer;

import com.akefirad.wiremock.compatiblizer.ReflectiveResponseDefinitionBuilder.ResponseDefinitionBuilder158;
import com.akefirad.wiremock.compatiblizer.ReflectiveResponseDefinitionBuilder.ResponseDefinitionBuilder251;
import com.akefirad.wiremock.compatiblizer.ReflectiveWireMock.WireMock158;
import com.akefirad.wiremock.compatiblizer.ReflectiveWireMock.WireMock251;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

public final class WireMockLibrary {
    private static final Logger log = getLogger(WireMockLibrary.class);

    private static final Class<WireMockServer> WIREMOCKSERVER_CLASS = WireMockServer.class;
    private static final Class<ResponseDefinitionBuilder> RESPONSEDEFINITIONBUILDER_CLASS = ResponseDefinitionBuilder.class;
    private static final Class<WireMock> WIREMOCK_CLASS = WireMock.class;
    private static final Class<?> MAPPINGBUILDER_CLASS = getMappingBuilderClass(); // Cannot be typed!
    private static final WireMockRunTime WIREMOCK_RUNTIME = getWiremockRuntime();

    public static ReflectiveWireMock wireMock() {
        return WIREMOCK_RUNTIME.getWireMock();
    }

    @SuppressWarnings("unchecked")
    public static <T extends ReflectiveResponseDefinitionBuilder> T newResponseDefinitionBuilder() {
        return (T) WIREMOCK_RUNTIME.getResponseDefinitionBuilderSupplier().get();
    }

    public static Method getStubForMethod() {
        return getMethod(WIREMOCKSERVER_CLASS, "stubFor", MAPPINGBUILDER_CLASS);
    }

    static Method getWillReturnMethod() {
        return getMethod(MAPPINGBUILDER_CLASS, "willReturn", RESPONSEDEFINITIONBUILDER_CLASS);
    }

    static Method getUrlPathEqualToMethod(Class<?> parameter) {
        return getMethod(WIREMOCK_CLASS, "urlPathEqualTo", parameter);
    }

    static Method getPostMethod(Class<?> parameter) {
        return getMethod(WIREMOCK_CLASS, "post", parameter);
    }

    static Method getGetMethod(Class<?> parameter) {
        return getMethod(WIREMOCK_CLASS, "get", parameter);
    }

    static Method getWithHeaderMethod(Class<?> parameter) {
        return getMethod(RESPONSEDEFINITIONBUILDER_CLASS, "withHeader", String.class, parameter);
    }

    @SneakyThrows
    private static Method getMethod(Class<?> clazz, String method, Class<?>... arguments) {
        return clazz.getMethod(method, arguments);
    }

    @SneakyThrows
    private static Class<?> getMappingBuilderClass() {
        return Class.forName("com.github.tomakehurst.wiremock.client.MappingBuilder");
    }

    private static String getWireMockVersion() {
        URL jarPath = WireMockServer.class.getProtectionDomain().getCodeSource().getLocation();
        String jarName;
        try {
            jarName = new File(jarPath.toURI()).getName();
        } catch (URISyntaxException e) {
            jarName = new File(jarPath.getPath()).getName();
        }
        String version = jarName.replace("wiremock-", "").replace(".jar", "");
        log.info("Detecting the version of WireMock as [{}]", version);
        return version;
    }

    private static WireMockRunTime getWiremockRuntime() {
        String version_old = "1.58";
        String version = getWireMockVersion();

        if (version.equals(version_old)) {
            return new WireMockRunTime(new WireMock158(), new Supplier<ReflectiveResponseDefinitionBuilder>() {
                public ReflectiveResponseDefinitionBuilder get() {
                    return new ResponseDefinitionBuilder158();
                }
            });
        } else {
            return new WireMockRunTime(new WireMock251(), new Supplier<ReflectiveResponseDefinitionBuilder>() {
                public ReflectiveResponseDefinitionBuilder get() {
                    return new ResponseDefinitionBuilder251();
                }
            });
        }
    }

    private interface Supplier<T> {
        T get();
    }

    private static class WireMockRunTime {
        private final ReflectiveWireMock wireMock;
        private final Supplier<ReflectiveResponseDefinitionBuilder> responseDefinitionBuilderSupplier;

        WireMockRunTime(ReflectiveWireMock wireMock, Supplier<ReflectiveResponseDefinitionBuilder> supplier) {
            requireNonNull(wireMock, "wireMock object cannot be null!");
            requireNonNull(wireMock, "ReflectiveResponseDefinitionBuilder supplier object cannot be null!");

            log.info("Constructing WireMockRunTime with wiremock: {}", wireMock);

            this.wireMock = wireMock;
            this.responseDefinitionBuilderSupplier = supplier;
        }

        ReflectiveWireMock getWireMock() {
            return wireMock;
        }

        Supplier<ReflectiveResponseDefinitionBuilder> getResponseDefinitionBuilderSupplier() {
            return responseDefinitionBuilderSupplier;
        }
    }
}
