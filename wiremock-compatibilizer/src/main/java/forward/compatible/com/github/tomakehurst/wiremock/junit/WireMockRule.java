package forward.compatible.com.github.tomakehurst.wiremock.junit;

import com.akefirad.wiremock.compatiblizer.ReflectiveMappingBuilder;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.core.Options;
import lombok.SneakyThrows;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getStubForMethod;

/**
 * Forward compatible WireMockRule
 */
public class WireMockRule extends com.github.tomakehurst.wiremock.junit.WireMockRule {
    public WireMockRule(Options options) {
        super(options);
    }

    public WireMockRule(int port) {
        super(port);
    }

    public WireMockRule(int port, Integer httpsPort) {
        super(port, httpsPort);
    }

    public WireMockRule() {
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <T> T stubFor(ReflectiveMappingBuilder reflectiveMappingBuilder) {
        return (T) getStubForMethod().invoke(this, reflectiveMappingBuilder.get());
    }

    @Deprecated
    public void stubFor(MappingBuilder mappingBuilder) {
        super.stubFor(mappingBuilder);
    }

}
