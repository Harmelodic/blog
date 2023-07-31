package com.harmelodic.blog.post;

import com.harmelodic.blog.post.contentful.ContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final ContentfulClient contentfulClient;

    PostService(ContentfulClient contentfulClient) {
        this.contentfulClient = contentfulClient;
    }

    public List<BlogPost> fetchAllBlogPosts() {
        return contentfulClient.fetchAllBlogPosts();
    }

    public BlogPost fetchPostById(String id) {
        return contentfulClient.fetchBlogPostById(id);
    }
}
