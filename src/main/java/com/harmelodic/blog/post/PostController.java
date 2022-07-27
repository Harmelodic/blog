package com.harmelodic.blog.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.fetchAllPosts();
    }

    @GetMapping("/{datePosted}")
    public Post getPostByDatePosted(@PathVariable("datePosted") String datePosted) {
        return postService.fetchPostByDatePosted(Integer.parseInt(datePosted));
    }
}
