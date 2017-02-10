package com.akefirad.wiremock;

import com.akefirad.wiremock.compatiblizer.samples.NonCompatibleWireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.rules.ExpectedException.none;

public class NonCompatibleWireMockTest {
    private NonCompatibleWireMock wireMock;

    @Rule
    public ExpectedException exception = none();

    @Before
    public void setUp() {
        wireMock = new NonCompatibleWireMock();
    }

    @After
    public void tearDown() {
        wireMock.getServer().stop();
    }

    @Test
    public void withOriginalWireMock_should_fail() {
        exception.expect(NoSuchMethodError.class);
        exception.expectMessage(equalTo("com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo(Ljava/lang/String;)Lcom/github/tomakehurst/wiremock/client/UrlMatchingStrategy;"));

        wireMock.stubForGetHello();
    }
}
