package com.harmelodic.blog.post;

public class FailedToFetchPostsException extends Exception {
    FailedToFetchPostsException(Throwable throwable) {
        super("Failed to fetch posts.", throwable);
    }
}
