package com.harmelodic.blog.post;

import java.util.UUID;

public record Post(UUID id,
                   String title,
                   String route,
                   Integer datePosted,
                   Integer lastUpdated,
                   String fileName,
                   String category) {
}
