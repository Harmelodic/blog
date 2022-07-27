package com.harmelodic.blog.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> fetchAllPosts() {
        return postRepository.fetchAllPosts();
    }

    public Post fetchPostByDatePosted(Integer datePosted) {
        return postRepository.fetchPostByDatePosted(datePosted);
    }
}
