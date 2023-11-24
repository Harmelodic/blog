package com.harmelodic.blog;

import com.harmelodic.blog.category.Category;
import com.harmelodic.blog.post.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Component
public class BlogContentfulClient {

    RestClient client;
    String baseUrl;
    String token;
    String space;
    String environment;

    BlogContentfulClient(RestClient.Builder builder,
                         @Value("${contentful.baseUrl}") String baseUrl,
                         @Value("${contentful.token}") String token,
                         @Value("${contentful.space}") String space,
                         @Value("${contentful.environment}") String environment) {
        this.client = builder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
        this.token = token;
        this.space = space;
        this.environment = environment;
    }

    public List<Post> fetchAllPosts() {
        ContentfulEntries contentfulEntries =
                client.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/spaces/{space_id}/environments/{environment_id}/entries")
                                .queryParam("access_token", token)
                                .queryParam("limit", 100)
                                .queryParam("order", "-sys.createdAt")
                                .queryParam("sys.contentType.sys.id", "blogPost")
                                .build(space, environment))
                        .retrieve()
                        .body(ContentfulEntries.class);

        if (contentfulEntries != null) {
            return contentfulEntries.items()
                    .stream()
                    .map(contentfulEntry -> new Post(
                            contentfulEntry.sys().id(),
                            contentfulEntry.fields().title(),
                            contentfulEntry.fields().content(),
                            contentfulEntry.metadata()
                                    .tags()
                                    .stream()
                                    .map(contentfulEntryMetadataTag -> contentfulEntryMetadataTag.sys().id())
                                    .toList()))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    record ContentfulEntries(List<ContentfulEntry> items) {
    }

    record ContentfulEntry(ContentfulSys sys,
                           ContentfulEntryFields fields,
                           ContentfulEntryMetadata metadata) {
    }

    record ContentfulSys(String id) {
    }

    record ContentfulEntryFields(Integer id,
                                 String title,
                                 String publishedOn,
                                 String content) {
    }

    record ContentfulEntryMetadata(List<ContentfulEntryMetadataTag> tags) {
    }

    record ContentfulEntryMetadataTag(ContentfulEntryMetadataTagSys sys) {
    }

    record ContentfulEntryMetadataTagSys(String id) {
    }

    public List<Category> fetchAllCategories() {
        ContentfulTags contentfulTags = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/spaces/{space_id}/environments/{environment_id}/tags")
                        .build(space, environment))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .body(ContentfulTags.class);

        if (contentfulTags != null) {
            return contentfulTags.items()
                    .stream()
                    .map(contentfulTag -> new Category(contentfulTag.sys().id(), contentfulTag.name()))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    record ContentfulTags(List<ContentfulTag> items) {
    }

    record ContentfulTag(String name,
                         ContentfulTagSys sys) {
    }

    record ContentfulTagSys(String id) {
    }

    public Post fetchPostById(String id) {
        ContentfulEntry contentfulEntry =
                client.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/spaces/{space_id}/environments/{environment_id}/entries/{entry_id}")
                                .queryParam("access_token", token)
                                .build(space, environment, id))
                        .retrieve()
                        .body(ContentfulEntry.class);

        if (contentfulEntry != null) {
            return new Post(contentfulEntry.sys().id(),
                    contentfulEntry.fields().title(),
                    contentfulEntry.fields().content(),
                    contentfulEntry.metadata()
                            .tags()
                            .stream()
                            .map(contentfulEntryMetadataTag -> contentfulEntryMetadataTag.sys().id())
                            .toList());
        } else {
            return null;
        }
    }
}
