package com.harmelodic.blog.category;

import com.harmelodic.blog.ContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final ContentfulClient contentfulClient;

    CategoryService(ContentfulClient contentfulClient) {
        this.contentfulClient = contentfulClient;
    }

    public List<Category> fetchAllCategories() {
        return contentfulClient.fetchAllCategories();
    }
}
