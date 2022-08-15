package com.harmelodic.blog.post;

import com.harmelodic.blog.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private final RowMapper<Post> postRowMapper = ((rs, rowNum) -> new Post(
            rs.getObject("id", UUID.class),
            rs.getString("title"),
            rs.getString("route"),
            rs.getInt("date_posted"),
            rs.getInt("last_updated"),
            rs.getString("file_name"),
            rs.getString("category")));

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
    void fetchPostByIdSuccess() {
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

        Post retrievedPost = repository.fetchPostById(postToFetch.id());

        assertEquals(postToFetch, retrievedPost);
    }

    @Test
    void createNewPostSuccess() {
        Post inputPost = new Post(null, "Some Post", "/post", 1234, 1234, "filename.md", "Something");

        repository.createNewPost(inputPost);

        List<Post> posts = jdbcTemplate.query("SELECT * FROM post", postRowMapper);

        assertEquals(1, posts.size());
        Post fetchedPost = posts.get(0);
        assertNotNull(fetchedPost.id());

        assertEquals(inputPost.title(), fetchedPost.title());
        assertEquals(inputPost.route(), fetchedPost.route());
        assertEquals(inputPost.datePosted(), fetchedPost.datePosted());
        assertEquals(inputPost.lastUpdated(), fetchedPost.lastUpdated());
        assertEquals(inputPost.fileName(), fetchedPost.fileName());
        assertEquals(inputPost.category(), fetchedPost.category());
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
