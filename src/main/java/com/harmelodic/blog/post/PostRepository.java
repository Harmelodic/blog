package com.harmelodic.blog.post;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Post> postRowMapper = ((rs, rowNum) -> new Post(
            rs.getObject("id", UUID.class),
            rs.getString("title"),
            rs.getString("route"),
            rs.getInt("date_posted"),
            rs.getInt("last_updated"),
            rs.getString("file_name"),
            rs.getString("category")));


    public List<Post> fetchAllPosts() {
        return jdbcTemplate.query("SELECT * FROM post;", postRowMapper);
    }

    public Post fetchPostByDatePosted(Integer datePosted) {
        return jdbcTemplate.queryForObject("SELECT * FROM post WHERE date_posted = ?", postRowMapper, datePosted);
    }

    private final RowMapper<String> categoryRowMapper = (((rs, rowNum) ->
            rs.getString("category")));

    public List<String> fetchAllCategories() {
        return jdbcTemplate.query("SELECT DISTINCT category FROM post ORDER BY category ASC", categoryRowMapper);
    }
}
