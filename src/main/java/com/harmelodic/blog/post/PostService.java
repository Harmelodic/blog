package com.harmelodic.blog.post;

import com.harmelodic.blog.BlogContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final BlogContentfulClient blogContentfulClient;

    PostService(BlogContentfulClient blogContentfulClient) {
        this.blogContentfulClient = blogContentfulClient;
    }

    public List<Post> fetchAllPosts() {
        return blogContentfulClient.fetchAllPosts();
    }

    public Post fetchPostById(String id) {
        return blogContentfulClient.fetchPostById(id);
    }
}
