package com.harmelodic.blog.post;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import org.springframework.beans.factory.annotation.Value;

public class ContentfulClient {
    final CDAClient client;

    ContentfulClient(@Value("${contentful.token}") String token,
                     @Value("${contentful.space}") String space,
                     @Value("${contentful.environment}") String environment) {
        client = CDAClient.builder().setToken(token)
                .setSpace(space)
                .setEnvironment(environment)
                .build();
    }

    CDAArray fetchAll() {
        return client.fetch(CDAEntry.class).all();
    }
}
