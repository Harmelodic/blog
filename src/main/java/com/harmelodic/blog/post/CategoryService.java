package com.harmelodic.blog.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final PostRepository postRepository;

    CategoryService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<String> fetchAllCategories() {
        return postRepository.fetchAllCategories();
    }
}
