package com.harmelodic.blog.category;

import com.harmelodic.blog.ContentfulBlogClient;
import com.harmelodic.blog.ContentfulBlogConnectionException;
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
        } catch (ContentfulBlogConnectionException e) {
            throw new FailedToFetchCategoriesException("Failed to fetch Categories.", e);
        }
    }
}
