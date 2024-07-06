package com.harmelodic.library;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "contentful-delivery-api")
class ContentfulClientTest {

    private static final String SELF = "harmelodic-blog";

    private static final String TOKEN = "example-token";
    private static final String SPACE = "space-id";
    private static final String ENVIRONMENT = "master";

    @Pact(consumer = SELF)
    public V4Pact fetchLibraryLinksWhenExist(PactDslWithProvider builder) throws IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("all-library-link-entries-response.json")) {
            String response = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);

            return builder
                    .given("library_links_exists")
                    .uponReceiving("a valid request for a list of library links")
                    .method("GET")
                    .matchPath(String.format("/spaces/%s/environments/%s/entries", SPACE, ENVIRONMENT))
                    .queryParameterFromProviderState("access_token", TOKEN, TOKEN)
                    .queryParameterFromProviderState("limit", "500", "500")
                    .queryParameterFromProviderState("sys.contentType.sys.id", "libraryLink", "libraryLink")
                    .willRespondWith()
                    .status(200)
                    .headers(Map.of(
                            "Content-Type", "application/json"
                    ))
                    .body(response)
                    .toPact(V4Pact.class);
        }
    }

    @Test
    @PactTestFor(pactMethod = "fetchLibraryLinksWhenExist")
    void testFetchLibraryLinksWhenExist(MockServer mockServer) throws ContentfulConnectionException {
        ContentfulClient customerClient = new ContentfulClient(RestClient.builder(), mockServer.getUrl(), TOKEN, SPACE, ENVIRONMENT);

        List<LibraryLink> receivedLibraryLinks = customerClient.fetchAllLibraryLinks();

        List<LibraryLink> expected = List.of(
                new LibraryLink(
                        "My Link Title",
                        "https://example.com",
                        "Some example category",
                        "https://example.com/favicon.ico")
        );
        assertEquals(expected, receivedLibraryLinks);
    }
}
