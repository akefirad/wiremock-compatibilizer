package com.akefirad.wiremock;

import com.akefirad.wiremock.compatiblizer.samples.ForwardCompatibleWireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ForwardCompatibleWireMockTest {
    private ForwardCompatibleWireMock wireMock;

    @Before
    public void setUp() {
        wireMock = new ForwardCompatibleWireMock();
        wireMock.stubForGetHello();
        wireMock.getServer().start();
    }

    @After
    public void tearDown() {
        wireMock.getServer().stop();
    }

    @Test
    public void withForwardCompatibleWireMock_should_succeed() {
        given()
                .port(wireMock.getPort())
                .get(wireMock.getUrl())
                .prettyPeek()
        .then()
                .statusCode(200)
                .body(equalTo(wireMock.getResponseBody()))
                .log()
        ;

        wireMock.getServer()
                .verify(getRequestedFor(urlEqualTo(wireMock.getUrl())));
    }
}
