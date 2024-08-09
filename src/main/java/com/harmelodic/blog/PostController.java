package com.harmelodic.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
@CrossOrigin
class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    record PostV1(String id,
                  String title,
                  String content,
                  List<String> categories) {
    }

    @GetMapping
    List<PostV1> getAllPosts() {
        try {
            return postService.fetchAllPosts()
                    .stream()
                    .map(post -> new PostV1(post.id(), post.title(), post.content(), post.categories()))
                    .toList();
        } catch (PostService.FailedToFetchPostsException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch Posts.", exception);
        }
    }

    @GetMapping("/{id}")
    Post getPostById(@PathVariable("id") String id) {
        return postService.fetchPostById(id);
    }
}
