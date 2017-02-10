package forward.compatible.com.github.tomakehurst.wiremock.client;

import com.akefirad.wiremock.compatiblizer.ReflectiveMappingBuilder;
import com.akefirad.wiremock.compatiblizer.ReflectiveResponseDefinitionBuilder;
import org.slf4j.Logger;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.newResponseDefinitionBuilder;
import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.wireMock;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Forward compatible WireMock
 */
public final class WireMock {
    private static final Logger log = getLogger(WireMock.class);

    private WireMock() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T urlPathEqualTo(String url) {
        log.debug("Executing forward compatible 'urlPathEqualTo' method...");
        return (T) wireMock().doUrlPathEqualTo(url);
    }

    public static <T> ReflectiveMappingBuilder post(T url) {
        log.debug("Executing forward compatible 'post' method...");
        return new ReflectiveMappingBuilder(wireMock().doPost(url));
    }

    public static <T> ReflectiveMappingBuilder get(T url) {
        log.debug("Executing forward compatible 'get' method...");
        return new ReflectiveMappingBuilder(wireMock().doGet(url));
    }

    public static ReflectiveResponseDefinitionBuilder aResponse() {
        log.debug("Executing forward compatible 'aResponse' method...");
        return newResponseDefinitionBuilder();
    }
}
