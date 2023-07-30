package com.harmelodic.blog.post;

import com.harmelodic.blog.post.contentful.ContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final PostRepository postRepository;
    private final ContentfulClient contentfulClient;

    CategoryService(PostRepository postRepository, ContentfulClient contentfulClient) {
        this.postRepository = postRepository;
        this.contentfulClient = contentfulClient;
    }

    public List<String> fetchAllCategories() {
        return postRepository.fetchAllCategories();
    }

    public List<Category> fetchAllCategoriesV2() {
        return contentfulClient.fetchAllCategories();
    }
}
