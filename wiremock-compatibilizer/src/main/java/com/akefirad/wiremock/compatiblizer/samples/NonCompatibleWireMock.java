package com.akefirad.wiremock.compatiblizer.samples;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.slf4j.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.slf4j.LoggerFactory.getLogger;

public class NonCompatibleWireMock {
    private static final Logger log = getLogger(ForwardCompatibleWireMock.class);

    private static final String RESPONSE_BODY = "Hello from non-compatible WireMockServer";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String URL = "/hello";

    private final WireMockRule rule;

    public NonCompatibleWireMock() {
        log.debug("Creating a non-compatible WireMockServer...");
        this.rule = new WireMockRule(0);
    }

    public void stubForGetHello() {
        log.debug("Initializing the non-compatible WireMockServer with stub for [GET {}] " +
                "with response status [200] and body [{}]...", URL, RESPONSE_BODY);

        rule.stubFor(get(urlEqualTo(URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, TEXT_PLAIN)
                        .withBody(RESPONSE_BODY)));
    }

    public int getPort() {
        return rule.port();
    }

    public WireMockServer getServer() {
        return rule;
    }

    public String getUrl() {
        return URL;
    }

    public String getResponseBody() {
        return RESPONSE_BODY;
    }

}
