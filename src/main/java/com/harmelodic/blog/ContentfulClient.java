package com.harmelodic.blog;

import com.harmelodic.blog.category.Category;
import com.harmelodic.blog.post.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ContentfulClient {

    RestTemplate client;
    String baseUrl;
    String token;
    String space;
    String environment;

    ContentfulClient(RestTemplateBuilder builder,
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

    public List<Post> fetchAllBlogPosts() {
        ContentfulEntriesResponseBody responseBody =
                client.getForObject("/spaces/{space_id}/environments/{environment_id}/entries" +
                                "?access_token={access_token}" +
                                "&limit=100" +
                                "&order=-sys.createdAt" +
                                "&sys.contentType.sys.id=blogPost",
                        ContentfulEntriesResponseBody.class,
                        Map.of(
                                "space_id", space,
                                "environment_id", environment,
                                "access_token", token
                        ));


        if (responseBody != null) {
            return responseBody.items()
                    .stream()
                    .map(contentfulEntry -> new Post(
                            contentfulEntry.sys().id(),
                            contentfulEntry.fields().title(),
                            contentfulEntry.fields().content()
                    ))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    record ContentfulEntriesResponseBody(List<ContentfulEntry> items) {
    }

    record ContentfulEntry(ContentfulSys sys,
                           ContentfulBlogPost fields) {
    }

    record ContentfulSys(String id) {
    }

    record ContentfulBlogPost(Integer id,
                              String title,
                              String publishedOn,
                              String content) {
    }

    public List<Category> fetchAllCategories() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        URI uri = URI.create(baseUrl + "/spaces/" + space + "/environments/" + environment + "/tags");

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

        ContentfulTagResponseBody responseBody = client.exchange(requestEntity, ContentfulTagResponseBody.class).getBody();

        if (responseBody != null) {
            return responseBody.items()
                    .stream()
                    .map(contentfulTag -> new Category(contentfulTag.sys().id(), contentfulTag.name()))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    record ContentfulTagResponseBody(List<ContentfulTag> items) {
    }

    record ContentfulTag(String name,
                         ContentfulTagSys sys) {
    }

    record ContentfulTagSys(String id) {
    }

    public Post fetchBlogPostById(String id) {
        // empty
        return null;
    }
}
