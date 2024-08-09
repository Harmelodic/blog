package com.harmelodic.blog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    ContentfulBlogClient contentfulBlogClient;

    @InjectMocks
    PostService postService;

    @Test
    void fetchAllPostsSuccess() throws ContentfulBlogClient.ContentfulBlogConnectionException {
        List<Post> postList = List.of(
                new Post("random-id", "Blog Title", "some content", List.of("anExampleTag")),
                new Post("random-id2", "Blog Title 2", "some other content", List.of("anExampleTag"))
        );
        when(contentfulBlogClient.fetchAllPosts()).thenReturn(postList);

        List<Post> retrievedPosts = assertDoesNotThrow(postService::fetchAllPosts);

        assertEquals(postList, retrievedPosts);
    }

    @Test
    void fetchAllPostsFail() throws ContentfulBlogClient.ContentfulBlogConnectionException {
        when(contentfulBlogClient.fetchAllPosts())
                .thenThrow(new ContentfulBlogClient.ContentfulBlogConnectionException("Failed to fetch Posts", new Throwable()));

        assertThrows(PostService.FailedToFetchPostsException.class, postService::fetchAllPosts);
    }

    @Test
    void fetchPostByIdSuccess() {
        Post post = new Post("random-id", "Blog Title", "some content", List.of("anExampleTag"));
        when(contentfulBlogClient.fetchPostById(post.id())).thenReturn(post);

        Post retrievedPost = postService.fetchPostById(post.id());

        assertEquals(post, retrievedPost);
    }

    @Test
    void fetchPostByIdFail() {
        String id = "random-id";
        when(contentfulBlogClient.fetchPostById(id)).thenThrow(new RuntimeException("Failed to fetch Post By ID"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostById(id));
    }
}
