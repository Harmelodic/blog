package com.harmelodic.blog;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CategoryService {

    private final ContentfulBlogClient contentfulBlogClient;

    CategoryService(ContentfulBlogClient contentfulBlogClient) {
        this.contentfulBlogClient = contentfulBlogClient;
    }

    List<Category> fetchAllCategories() throws FailedToFetchCategoriesException {
        try {
            return contentfulBlogClient.fetchAllCategories();
        } catch (ContentfulBlogClient.ContentfulBlogConnectionException exception) {
            throw new FailedToFetchCategoriesException(exception);
        }
    }

    static class FailedToFetchCategoriesException extends Exception {
        FailedToFetchCategoriesException(Throwable throwable) {
            super("Failed to fetch Categories.", throwable);
        }
    }
}
