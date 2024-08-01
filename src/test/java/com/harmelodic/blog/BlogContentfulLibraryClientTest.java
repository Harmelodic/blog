package com.harmelodic.blog;

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
class BlogContentfulLibraryClientTest {

    private static final String SELF = "harmelodic-blog";

    private static final String TOKEN = "example-token";
    private static final String SPACE = "space-id";
    private static final String ENVIRONMENT = "master";
    private static final String EXAMPLE_ID = "1234abcd1234abcd123abc";

    @Pact(consumer = SELF)
    public V4Pact fetchPostsWhenExist(PactDslWithProvider builder) throws IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("all-blog-post-entries-response.json")) {
            assert resourceAsStream != null;
            String response = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);

            return builder
                    .given("blog_post_exists")
                    .uponReceiving("a valid request for a list of blog posts")
                    .method("GET")
                    .matchPath(String.format("/spaces/%s/environments/%s/entries", SPACE, ENVIRONMENT))
                    .queryParameterFromProviderState("access_token", TOKEN, TOKEN)
                    .queryParameterFromProviderState("limit", "100", "100")
                    .queryParameterFromProviderState("order", "-sys.createdAt", "-sys.createdAt")
                    .queryParameterFromProviderState("sys.contentType.sys.id", "blogPost", "blogPost")
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
    @PactTestFor(pactMethod = "fetchPostsWhenExist")
    void testFetchPostsWhenExist(MockServer mockServer) throws ContentfulBlogClient.ContentfulBlogConnectionException {
        ContentfulBlogClient customerClient = new ContentfulBlogClient(RestClient.builder(), mockServer.getUrl(), TOKEN, SPACE, ENVIRONMENT);

        List<Post> receivedPosts = customerClient.fetchAllPosts();

        List<Post> expected = List.of(
                new Post(
                        "my-blog-title",
                        "My Blog Title",
                        "Some example content",
                        List.of("anExampleTag"))
        );
        assertEquals(expected, receivedPosts);
    }

    @Pact(consumer = SELF)
    public V4Pact fetchCategoriesWhenExist(PactDslWithProvider builder) throws IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("tags-response.json")) {
            assert resourceAsStream != null;
            String response = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);

            return builder
                    .given("categories_exist")
                    .uponReceiving("a valid request for a list of tags")
                    .method("GET")
                    .matchPath(String.format("/spaces/%s/environments/%s/tags", SPACE, ENVIRONMENT))
                    .headers("Authorization", "Bearer " + TOKEN)
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
    @PactTestFor(pactMethod = "fetchCategoriesWhenExist")
    void testFetchCategoriesWhenExist(MockServer mockServer) throws ContentfulBlogClient.ContentfulBlogConnectionException {
        ContentfulBlogClient customerClient = new ContentfulBlogClient(RestClient.builder(), mockServer.getUrl(), TOKEN, SPACE, ENVIRONMENT);

        List<Category> receivedCategories = customerClient.fetchAllCategories();

        List<Category> expected = List.of(
                new Category("engineeringPrinciples", "Engineering Principles"),
                new Category("blog", "Blog"),
                new Category("review", "Review"));

        assertEquals(expected, receivedCategories);
    }


    @Pact(consumer = SELF)
    public V4Pact fetchPostByIdWhenExist(PactDslWithProvider builder) throws IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("single-entry-response.json")) {
            assert resourceAsStream != null;
            String response = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);

            return builder
                    .given("blog_post_exists")
                    .uponReceiving("a valid request for a single blog_post")
                    .method("GET")
                    .matchPath(String.format("/spaces/%s/environments/%s/entries", SPACE, ENVIRONMENT))
                    .queryParameterFromProviderState("access_token", TOKEN, TOKEN)
                    .queryParameterFromProviderState("content_type", "blogPost", "blogPost")
                    .queryParameterFromProviderState("fields.id", EXAMPLE_ID, EXAMPLE_ID)
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
    @PactTestFor(pactMethod = "fetchPostByIdWhenExist")
    void testFetchPostByIdWhenExist(MockServer mockServer) {
        ContentfulBlogClient customerClient = new ContentfulBlogClient(RestClient.builder(), mockServer.getUrl(), TOKEN, SPACE, ENVIRONMENT);

        Post receivedPost = customerClient.fetchPostById(EXAMPLE_ID);

        Post expected = new Post(
                EXAMPLE_ID,
                "My Blog Title",
                "Some example content",
                List.of("anExampleTag"));
        assertEquals(expected, receivedPost);
    }
}
