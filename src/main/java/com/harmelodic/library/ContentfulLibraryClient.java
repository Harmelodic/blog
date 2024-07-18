package com.harmelodic.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Component
public class ContentfulLibraryClient {
    private static final Logger logger = LoggerFactory.getLogger(ContentfulLibraryClient.class);

    private final RestClient client;
    private final String token;
    private final String space;
    private final String environment;

    ContentfulLibraryClient(RestClient.Builder builder,
                            @Value("${contentful.baseUrl}") String baseUrl,
                            @Value("${contentful.token}") String token,
                            @Value("${contentful.space}") String space,
                            @Value("${contentful.environment}") String environment) {
        this.client = builder.baseUrl(baseUrl).build();
        this.token = token;
        this.space = space;
        this.environment = environment;
    }

    public List<LibraryLink> fetchAllLibraryLinks() throws ContentfulLibraryConnectionException {
        try {
            ContentfulEntries contentfulEntries =
                    client.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/spaces/{space_id}/environments/{environment_id}/entries")
                                    .queryParam("access_token", token)
                                    .queryParam("limit", 500)
                                    .queryParam("sys.contentType.sys.id", "libraryLink")
                                    .build(space, environment))
                            .retrieve()
                            .body(ContentfulEntries.class);

            if (contentfulEntries != null) {
                return contentfulEntries.items()
                        .stream()
                        .map(contentfulEntry -> new LibraryLink(
                                contentfulEntry.fields().title(),
                                contentfulEntry.fields().href(),
                                contentfulEntry.fields().category(),
                                contentfulEntry.fields().favicon()
                        ))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            logger.atError()
                    .addKeyValue("status", exception.getStatusCode())
                    .addKeyValue("exceptionMessage", exception.getMessage())
                    .log("Failed to fetch Library links from Contentful.");
            throw new ContentfulLibraryConnectionException("Failed to fetch Library links from Contentful.", exception);
        }
    }

    record ContentfulEntries(List<ContentfulEntry> items) {
    }

    record ContentfulEntry(ContentfulEntryFields fields) {
    }

    record ContentfulEntryFields(String title,
                                 String href,
                                 String category,
                                 String favicon) {
    }
}
