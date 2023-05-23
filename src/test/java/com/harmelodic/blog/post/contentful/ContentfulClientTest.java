package com.harmelodic.blog.post.contentful;

import com.harmelodic.blog.post.BlogPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ContentfulClient.class, SpringContentfulConfig.class})
class ContentfulClientTest {

    @Autowired
    ContentfulClient client;

    @Test
    void fetchAll() {
        List<BlogPost> posts = client.fetchAllBlogPosts();

        assertNotNull(posts);
    }
}
