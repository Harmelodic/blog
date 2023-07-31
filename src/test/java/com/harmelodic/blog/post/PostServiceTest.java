package com.harmelodic.blog.post;

import com.harmelodic.blog.ContentfulClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    ContentfulClient contentfulClient;

    @InjectMocks
    PostService postService;

    @Test
    void fetchAllPostsSuccess() {
        List<Post> postList = List.of(
                new Post("random-id", "Blog Title", "some content"),
                new Post("random-id2", "Blog Title 2", "some other content")
        );
        when(contentfulClient.fetchAllPosts()).thenReturn(postList);

        List<Post> retrievedAccounts = postService.fetchAllPosts();

        assertEquals(postList, retrievedAccounts);
    }

    @Test
    void fetchAllPostsFail() {
        when(contentfulClient.fetchAllPosts()).thenThrow(new RuntimeException("Failed to fetch Posts"));

        assertThrows(RuntimeException.class, () -> postService.fetchAllPosts());
    }

    @Test
    void fetchPostByIdSuccess() {
        Post post = new Post("random-id", "Blog Title", "some content");
        when(contentfulClient.fetchPostById(post.id())).thenReturn(post);

        Post receivedAccount = postService.fetchPostById(post.id());

        assertEquals(post, receivedAccount);
    }

    @Test
    void fetchPostByIdFail() {
        String id = "random-id";
        when(contentfulClient.fetchPostById(id)).thenThrow(new RuntimeException("Failed to fetch Account By ID"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostById(id));
    }
}
