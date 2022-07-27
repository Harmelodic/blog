package com.harmelodic.blog.post;

import com.harmelodic.blog.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        Application.class,
        PostRepository.class
})
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PostRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("""
                TRUNCATE TABLE post;
                    """);
    }

    @Test
    void fetchAllPostsSuccess() {
        List<Post> inputPosts = List.of(
                new Post(UUID.randomUUID(), "Some Post", "/post", 1234, 1234, "filename.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 2", "/post", 12345, 12345, "filename2.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 3", "/list", 12345, 12345, "filename3.md", "Something Else")
        );
        inputPosts.forEach(post -> jdbcTemplate.update("""
                        INSERT INTO post(id, `title`, route, date_posted, last_updated, file_name, category)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """,
                post.id(),
                post.title(),
                post.route(),
                post.datePosted(),
                post.lastUpdated(),
                post.fileName(),
                post.category()));


        List<Post> retrievedPosts = repository.fetchAllPosts();

        assertEquals(inputPosts, retrievedPosts);
    }


    @Test
    void fetchPostByDatePostedSuccess() {
        List<Post> inputPosts = List.of(
                new Post(UUID.randomUUID(), "Some Post", "/post", 1234, 1234, "filename.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 2", "/post", 12345, 12345, "filename2.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 3", "/list", 12346, 12346, "filename3.md", "Something Else")
        );
        inputPosts.forEach(post -> jdbcTemplate.update("""
                        INSERT INTO post(id, `title`, route, date_posted, last_updated, file_name, category)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """,
                post.id(),
                post.title(),
                post.route(),
                post.datePosted(),
                post.lastUpdated(),
                post.fileName(),
                post.category()));

        Post postToFetch = inputPosts.get(0);

        Post retrievedPost = repository.fetchPostByDatePosted(postToFetch.datePosted());

        assertEquals(postToFetch, retrievedPost);
    }


    @Test
    void fetchAllCategoriesSuccess() {
        List<Post> inputPosts = List.of(
                new Post(UUID.randomUUID(), "Some Post", "/post", 1234, 1234, "filename.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 2", "/post", 12345, 12345, "filename2.md", "Something"),
                new Post(UUID.randomUUID(), "Some Post 3", "/list", 12346, 12346, "filename3.md", "Something Else")
        );
        inputPosts.forEach(post -> jdbcTemplate.update("""
                        INSERT INTO post(id, `title`, route, date_posted, last_updated, file_name, category)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """,
                post.id(),
                post.title(),
                post.route(),
                post.datePosted(),
                post.lastUpdated(),
                post.fileName(),
                post.category()));

        List<String> originalCategories = inputPosts.stream()
                .map(post -> post.category())
                .distinct()
                .toList();

        List<String> retrievedCategories = repository.fetchAllCategories();

        assertEquals(originalCategories, retrievedCategories);
    }
}
