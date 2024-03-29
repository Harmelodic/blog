package com.harmelodic.blog.post;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
@CrossOrigin
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.fetchAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postService.fetchPostById(id);
    }
}
