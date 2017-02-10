package forward.compatible.com.github.tomakehurst.wiremock;

import com.akefirad.wiremock.compatiblizer.ReflectiveMappingBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.core.Options;
import lombok.SneakyThrows;

import static com.akefirad.wiremock.compatiblizer.WireMockLibrary.getStubForMethod;

/**
 * Forward compatible WireMockServer
 */
public class WireMockServer extends com.github.tomakehurst.wiremock.WireMockServer {
    public WireMockServer(Options options) {
        super(options);
    }

    public WireMockServer(int port, Integer httpsPort, FileSource fileSource, boolean enableBrowserProxying, ProxySettings proxySettings, Notifier notifier) {
        super(port, httpsPort, fileSource, enableBrowserProxying, proxySettings, notifier);
    }

    public WireMockServer(int port, FileSource fileSource, boolean enableBrowserProxying, ProxySettings proxySettings) {
        super(port, fileSource, enableBrowserProxying, proxySettings);
    }

    public WireMockServer(int port, FileSource fileSource, boolean enableBrowserProxying) {
        super(port, fileSource, enableBrowserProxying);
    }

    public WireMockServer(int port) {
        super(port);
    }

    public WireMockServer(int port, Integer httpsPort) {
        super(port, httpsPort);
    }

    public WireMockServer() {
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T stubFor(ReflectiveMappingBuilder reflectiveMappingBuilder) {
        return (T) getStubForMethod().invoke(this, reflectiveMappingBuilder.get());
    }

}
