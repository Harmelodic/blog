package com.harmelodic.blog;

import java.util.List;

record Post(String id,
            String title,
            String content,
            List<String> categories) {
}
