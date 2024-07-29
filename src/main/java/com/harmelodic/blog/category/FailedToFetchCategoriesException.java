package com.harmelodic.blog.category;

public class FailedToFetchCategoriesException extends Exception {
    FailedToFetchCategoriesException(Throwable throwable) {
        super("Failed to fetch Categories.", throwable);
    }
}
