package com.harmelodic.blog.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ContentfulClient.class)
class ContentfulClientTest {

    @Autowired
    ContentfulClient client;

    @Test
    void fetchAll() {
        List<BlogPost> posts = client.fetchAllBlogPosts();

        assertNotNull(posts);
    }
}
