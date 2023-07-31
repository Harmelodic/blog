package com.harmelodic.blog.post;

import com.harmelodic.blog.post.contentful.ContentfulClient;
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
        List<BlogPost> postList = List.of(
                new BlogPost("random-id", "Blog Title", "some content"),
                new BlogPost("random-id2", "Blog Title 2", "some other content")
        );
        when(contentfulClient.fetchAllBlogPosts()).thenReturn(postList);

        List<BlogPost> retrievedAccounts = postService.fetchAllBlogPosts();

        assertEquals(postList, retrievedAccounts);
    }

    @Test
    void fetchAllPostsFail() {
        when(contentfulClient.fetchAllBlogPosts()).thenThrow(new RuntimeException("Failed to fetch Posts"));

        assertThrows(RuntimeException.class, () -> postService.fetchAllBlogPosts());
    }

    @Test
    void fetchPostByIdSuccess() {
        BlogPost post = new BlogPost("random-id", "Blog Title", "some content");
        when(contentfulClient.fetchBlogPostById(post.id())).thenReturn(post);

        BlogPost receivedAccount = postService.fetchPostById(post.id());

        assertEquals(post, receivedAccount);
    }

    @Test
    void fetchPostByIdFail() {
        String id = "random-id";
        when(contentfulClient.fetchBlogPostById(id)).thenThrow(new RuntimeException("Failed to fetch Account By ID"));

        assertThrows(RuntimeException.class, () -> postService.fetchPostById(id));
    }
}
