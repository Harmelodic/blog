package com.harmelodic.blog.post.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.harmelodic.blog.post.BlogPost;
import org.springframework.stereotype.Component;

import java.util.List;

// https://contentful.github.io/contentful.java/
@Component
public class ContentfulClient {

    final String LOCALE = "en-GB";

    final CDAClient client;

    ContentfulClient(CDAClient client) {
        this.client = client;
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
