package com.harmelodic.blog.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
    void fetchAllPostsFail() {
        when(postRepository.fetchAllPosts()).thenThrow(new RuntimeException("Failed to fetch Posts"));

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
        when(postRepository.fetchPostByDatePosted(datePosted)).thenThrow(new RuntimeException("Failed to fetch Post by Date Posted"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostByDatePosted(datePosted));
    }

    @Test
    void fetchPostByIdSuccess() {
        Post post = new Post(UUID.randomUUID(), "Post Title", "/post", 123, 123, "filename.md", "Something");
        when(postRepository.fetchPostById(post.id())).thenReturn(post);

        Post receivedAccount = postService.fetchPostById(post.id());

        assertEquals(post, receivedAccount);
    }

    @Test
    void fetchPostByIdFail() {
        UUID id = UUID.randomUUID();
        when(postRepository.fetchPostById(id)).thenThrow(new RuntimeException("Failed to fetch Account By ID"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostById(id));
    }

    @Test
    void createNewPostSuccess() {
        Post inputPost = new Post(null, "Post Title", "/post", 123, 123, "filename.md", "Something");

        doNothing().when(postRepository).createNewPost(inputPost);

        assertDoesNotThrow(() -> postService.createNewPost(inputPost));
    }

    @Test
    void createNewPostFail() {
        Post inputPost = new Post(null, "Post Title", "/post", 123, 123, "filename.md", "Something");

        doThrow(new RuntimeException("Failed to create Post")).when(postRepository).createNewPost(inputPost);

        assertThrows(RuntimeException.class, () -> postService.createNewPost(inputPost));
    }
}
