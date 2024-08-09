package com.harmelodic.blog;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PostService {

    private final ContentfulBlogClient contentfulBlogClient;

    PostService(ContentfulBlogClient contentfulBlogClient) {
        this.contentfulBlogClient = contentfulBlogClient;
    }

    List<Post> fetchAllPosts() throws FailedToFetchPostsException {
        try {
            return contentfulBlogClient.fetchAllPosts();
        } catch (ContentfulBlogClient.ContentfulBlogConnectionException exception) {
            throw new FailedToFetchPostsException(exception);
        }
    }

    Post fetchPostById(String id) {
        return contentfulBlogClient.fetchPostById(id);
    }

    static class FailedToFetchPostsException extends Exception {
        FailedToFetchPostsException(Throwable throwable) {
            super("Failed to fetch posts.", throwable);
        }
    }
}
