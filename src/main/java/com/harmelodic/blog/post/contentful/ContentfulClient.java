package com.harmelodic.blog.post.contentful;

import com.harmelodic.blog.post.BlogPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ContentfulClient {

    RestTemplate client;
    String token;
    String space;
    String environment;

    ContentfulClient(RestTemplateBuilder builder,
                     @Value("${contentful.baseUrl}") String baseUrl,
                     @Value("${contentful.token}") String token,
                     @Value("${contentful.space}") String space,
                     @Value("${contentful.environment}") String environment) {
        this.client = builder.rootUri(baseUrl).build();
        this.token = token;
        this.space = space;
        this.environment = environment;
    }

    public List<BlogPost> fetchAllBlogPosts() {
        ContentfulBlogPostResponseBody responseBody =
                client.getForObject("/spaces/{space}/environments/{environment}/entries" +
                                "?access_token={token}" +
                                "&limit=100" +
                                "&order=-sys.createdAt" +
                                "&sys.contentType.sys.id=blogPost",
                        ContentfulBlogPostResponseBody.class,
                        Map.of(
                                "space", space,
                                "environment", environment,
                                "token", token
                        ));


        if (responseBody != null) {
            return responseBody.items()
                    .stream()
                    .map(contentfulBlogPost -> new BlogPost(
                            contentfulBlogPost.sys().id(),
                            contentfulBlogPost.fields().title(),
                            contentfulBlogPost.fields().content()
                    ))
                    .toList();
        } else {
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
