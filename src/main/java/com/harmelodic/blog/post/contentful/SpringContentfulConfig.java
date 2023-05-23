package com.harmelodic.blog.post.contentful;

import com.contentful.java.cda.CDAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContentfulConfig {
    @Value("${contentful.token}")
    String token;

    @Value("${contentful.space}")
    String space;

    @Value("${contentful.environment}")
    String environment;

    @Bean
    CDAClient cdaClient() {
        return CDAClient.builder()
                .setToken(token)
                .setSpace(space)
                .setEnvironment(environment)
                .build();
    }
}
