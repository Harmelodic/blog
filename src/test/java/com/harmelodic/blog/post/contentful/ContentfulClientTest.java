package com.harmelodic.blog.post.contentful;

import com.harmelodic.blog.post.BlogPost;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContentfulClientTest {

    ContentfulClient client;

    @Test
    void fetchAll() {
        client = new ContentfulClient(WebClient.builder(),
                "",
                "ei54cjka6crw",
                "master");
        List<BlogPost> posts = client.fetchAllBlogPosts();

        posts.forEach(blogPost -> System.out.println(blogPost.id() + " - " + blogPost.title()));
        assertNotNull(posts);
    }
}
