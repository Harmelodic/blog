package com.harmelodic.blog.post.contentful;

import com.harmelodic.blog.post.BlogPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ContentfulClient {

    WebClient client;
    String token;
    String space;
    String environment;

    ContentfulClient(WebClient.Builder builder,
                     @Value("${contentful.token}") String token,
                     @Value("${contentful.space}") String space,
                     @Value("${contentful.environment}") String environment) {
        this.client = builder.baseUrl("https://cdn.contentful.com").build();
        this.token = token;
        this.space = space;
        this.environment = environment;
    }

    List<BlogPost> fetchAllBlogPosts() {
        ContentfulBlogPostResponseBody response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/spaces/{space}/environments/{environment}/entries")
                        .queryParam("access_token", List.of(token))
                        .queryParam("limit", 100)
                        .queryParam("order", "-sys.createdAt")
                        .queryParam("sys.contentType.sys.id", "blogPost")
                        .build(Map.of(
                                "space", space,
                                "environment", environment
                        )))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(ContentfulBlogPostResponseBody.class))
                .block();


        if (response != null) {
            return response.items()
                    .stream()
                    .map(contentfulBlogPost -> new BlogPost(
                            contentfulBlogPost.sys().id(),
                            contentfulBlogPost.fields().title(),
                            contentfulBlogPost.fields().content()
                    ))
                    .toList();
        }
        else {
            return Collections.emptyList();
        }
    }

    record ContentfulBlogPostResponseBody(List<ContentfulEntry> items) {
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
}
