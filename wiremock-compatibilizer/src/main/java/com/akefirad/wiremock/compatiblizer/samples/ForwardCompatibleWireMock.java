package com.akefirad.wiremock.compatiblizer.samples;

import forward.compatible.com.github.tomakehurst.wiremock.WireMockServer;
import org.slf4j.Logger;

import static forward.compatible.com.github.tomakehurst.wiremock.client.WireMock.get;
import static forward.compatible.com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static forward.compatible.com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.slf4j.LoggerFactory.getLogger;

public class ForwardCompatibleWireMock {
    private static final Logger log = getLogger(ForwardCompatibleWireMock.class);

    private static final String RESPONSE_BODY = "Hello from non-compatible WireMockServer";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String URL = "/hello";

    private final WireMockServer server;

    public ForwardCompatibleWireMock() {
        log.debug("Creating a forward-compatible WireMockServer...");
        this.server = new WireMockServer(0);
    }

    public void stubForGetHello() {
        log.debug("Initializing the forward-compatible WireMockServer with stub for [GET {}] " +
                "with response status [200] and body [{}]...", URL, RESPONSE_BODY);

        server.stubFor(get(urlPathEqualTo(URL))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, TEXT_PLAIN)
                        .withBody(RESPONSE_BODY)));
    }

    public WireMockServer getServer() {
        return server;
    }

    public int getPort() {
        return server.port();
    }

    public String getUrl() {
        return URL;
    }

    public String getResponseBody() {
        return RESPONSE_BODY;
    }

}
