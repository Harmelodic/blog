package com.harmelodic.blog.category;

import com.harmelodic.blog.ContentfulBlogClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final ContentfulBlogClient contentfulBlogClient;

    CategoryService(ContentfulBlogClient contentfulBlogClient) {
        this.contentfulBlogClient = contentfulBlogClient;
    }

    public List<Category> fetchAllCategories() throws FailedToFetchCategoriesException {
        try {
            return contentfulBlogClient.fetchAllCategories();
        } catch (ContentfulBlogClient.ContentfulBlogConnectionException exception) {
            throw new FailedToFetchCategoriesException(exception);
        }
    }

    public static class FailedToFetchCategoriesException extends Exception {
        FailedToFetchCategoriesException(Throwable throwable) {
            super("Failed to fetch Categories.", throwable);
        }
    }
}
