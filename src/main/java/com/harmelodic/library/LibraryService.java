package com.harmelodic.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final ContentfulLibraryClient contentfulLibraryClient;

    public LibraryService(ContentfulLibraryClient contentfulLibraryClient) {
        this.contentfulLibraryClient = contentfulLibraryClient;
    }

    List<LibraryLink> fetchLibrary() throws FailedToFetchLibraryException {
        try {
            return contentfulLibraryClient.fetchAllLibraryLinks();
        } catch (ContentfulLibraryConnectionException exception) {
            throw new FailedToFetchLibraryException("Failed to fetch Library Links", exception);
        }
    }
}
