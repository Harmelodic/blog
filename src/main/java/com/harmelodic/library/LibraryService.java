package com.harmelodic.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class LibraryService {

    private final ContentfulLibraryClient contentfulLibraryClient;

    LibraryService(ContentfulLibraryClient contentfulLibraryClient) {
        this.contentfulLibraryClient = contentfulLibraryClient;
    }

    List<LibraryLink> fetchLibrary() throws FailedToFetchLibraryException {
        try {
            return contentfulLibraryClient.fetchAllLibraryLinks();
        } catch (ContentfulLibraryClient.ContentfulLibraryConnectionException exception) {
            throw new FailedToFetchLibraryException("Failed to fetch Library Links", exception);
        }
    }

    static class FailedToFetchLibraryException extends Exception {
        FailedToFetchLibraryException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
