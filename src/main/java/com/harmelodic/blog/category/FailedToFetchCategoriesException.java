package com.harmelodic.blog.category;

public class FailedToFetchCategoriesException extends Exception {
    FailedToFetchCategoriesException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
