package com.harmelodic.blog.post;

import java.util.List;

public record Post(String id,
                   String title,
                   String content,
                   List<String> categories) {
}
