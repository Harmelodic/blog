package com.harmelodic.blog.category;

import com.harmelodic.blog.BlogContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final BlogContentfulClient blogContentfulClient;

    CategoryService(BlogContentfulClient blogContentfulClient) {
        this.blogContentfulClient = blogContentfulClient;
    }

    public List<Category> fetchAllCategories() {
        return blogContentfulClient.fetchAllCategories();
    }
}
