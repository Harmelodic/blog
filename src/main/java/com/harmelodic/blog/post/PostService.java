package com.harmelodic.blog.post;

import com.harmelodic.blog.ContentfulBlogClient;
import com.harmelodic.blog.ContentfulBlogConnectionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final ContentfulBlogClient contentfulBlogClient;

    PostService(ContentfulBlogClient contentfulBlogClient) {
        this.contentfulBlogClient = contentfulBlogClient;
    }

    public List<Post> fetchAllPosts() throws FailedToFetchPostsException {
        try {
            return contentfulBlogClient.fetchAllPosts();
        } catch (ContentfulBlogConnectionException contentfulBlogConnectionException) {
            throw new FailedToFetchPostsException(contentfulBlogConnectionException);
        }
    }

    public Post fetchPostById(String id) {
        return contentfulBlogClient.fetchPostById(id);
    }
}
