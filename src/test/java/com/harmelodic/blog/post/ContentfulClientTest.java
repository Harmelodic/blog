package com.harmelodic.blog.post;

import com.contentful.java.cda.CDAArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ContentfulClient.class)
class ContentfulClientTest {

    @Autowired
    ContentfulClient client;

    @Test
    void fetchAll() {
        CDAArray all = client.fetchAll();

        assertNotNull(all);
    }
}