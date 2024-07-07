package com.harmelodic.blog.post;

public class FailedToFetchPostsException extends Exception {
    FailedToFetchPostsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
