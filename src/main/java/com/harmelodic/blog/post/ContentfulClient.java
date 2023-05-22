package com.harmelodic.blog.post;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

// https://contentful.github.io/contentful.java/
public class ContentfulClient {

    final String LOCALE = "en-GB";

    final CDAClient client;

    ContentfulClient(@Value("${contentful.token}") String token,
                     @Value("${contentful.space}") String space,
                     @Value("${contentful.environment}") String environment) {
        client = CDAClient.builder().setToken(token)
                .setSpace(space)
                .setEnvironment(environment)
                .build();
    }

    List<BlogPost> fetchAllBlogPosts() {
        return client.fetch(CDAEntry.class)
                .withContentType("blogPost")
                .orderBy("-sys.createdAt")
                .all()
                .entries()
                .values()
                .stream()
                .map(cdaEntry -> new BlogPost(
                        cdaEntry.id(),
                        cdaEntry.getField(LOCALE, "title"),
                        cdaEntry.getField(LOCALE, "content")
                ))
                .toList();
    }
}
