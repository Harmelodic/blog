package com.harmelodic.blog.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    void fetchAllPostsSuccess() {
        List<Post> postList = List.of(
                new Post(UUID.randomUUID(), "Post Title", "/post", 123, 123, "filename.md", "Something"),
                new Post(UUID.randomUUID(), "Post Title", "/post", 123, 123, "filename.md", "Something")
        );
        when(postRepository.fetchAllPosts()).thenReturn(postList);

        List<Post> retrievedAccounts = postService.fetchAllPosts();

        assertEquals(postList, retrievedAccounts);
    }

    @Test
    void fetchALlPostsFail() {
        when(postRepository.fetchAllPosts()).thenThrow(new RuntimeException("Failed to fetch Accounts"));

        assertThrows(RuntimeException.class, () -> postService.fetchAllPosts());
    }

    @Test
    void fetchPostByDatePostedSuccess() {
        Post post = new Post(UUID.randomUUID(), "Post Title", "/post", 123, 123, "filename.md", "Something");
        when(postRepository.fetchPostByDatePosted(post.datePosted())).thenReturn(post);

        Post receivedAccount = postService.fetchPostByDatePosted(post.datePosted());

        assertEquals(post, receivedAccount);
    }

    @Test
    void fetchPostByDatePostedFail() {
        int datePosted = 123;
        when(postRepository.fetchPostByDatePosted(datePosted)).thenThrow(new RuntimeException("Failed to open account"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostByDatePosted(datePosted));
    }
}
