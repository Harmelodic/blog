package com.harmelodic.blog.post;

import com.harmelodic.blog.post.contentful.ContentfulClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ContentfulClient contentfulClient;

    PostService(PostRepository postRepository, ContentfulClient contentfulClient) {
        this.postRepository = postRepository;
        this.contentfulClient = contentfulClient;
    }


    public List<Post> fetchAllPosts() {
        return postRepository.fetchAllPosts();
    }

    public List<BlogPost> fetchAllBlogPosts() {
        return contentfulClient.fetchAllBlogPosts();
    }

    public Post fetchPostByDatePosted(Integer datePosted) {
        return postRepository.fetchPostByDatePosted(datePosted);
    }

    public Post fetchPostById(UUID id) {
        return postRepository.fetchPostById(id);
    }

    public void createNewPost(Post postToCreate) {
        postRepository.createNewPost(postToCreate);
    }
}
