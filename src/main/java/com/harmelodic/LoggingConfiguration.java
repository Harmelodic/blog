package com.harmelodic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

@Configuration
public class LoggingConfiguration {

    @Bean
    public ServletContextRequestLoggingFilter servletContextRequestLoggingFilter() {
        ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter();
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(false); // Payload may include sensitive data
        return filter;
    }
}
