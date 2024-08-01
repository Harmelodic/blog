package com.harmelodic.blog;

import java.util.List;

public record Post(String id,
                   String title,
                   String content,
                   List<String> categories) {
}
