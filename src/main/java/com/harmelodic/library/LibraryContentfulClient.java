package com.harmelodic.library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class LibraryContentfulClient {

    RestTemplate client;
    String baseUrl;
    String token;
    String space;
    String environment;

    LibraryContentfulClient(RestTemplateBuilder builder,
                            @Value("${contentful.baseUrl}") String baseUrl,
                            @Value("${contentful.token}") String token,
                            @Value("${contentful.space}") String space,
                            @Value("${contentful.environment}") String environment) {
        this.client = builder.rootUri(baseUrl).build();
        this.baseUrl = baseUrl;
        this.token = token;
        this.space = space;
        this.environment = environment;
    }

    public List<LibraryLink> fetchAllLibraryLinks() {
        ContentfulEntries responseBody =
                client.getForObject("/spaces/{space_id}/environments/{environment_id}/entries" +
                                "?access_token={access_token}" +
                                "&sys.contentType.sys.id=libraryLink",
                        ContentfulEntries.class,
                        Map.of(
                                "space_id", space,
                                "environment_id", environment,
                                "access_token", token
                        ));


        if (responseBody != null) {
            return responseBody.items()
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
