package com.harmelodic.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final ContentfulClient contentfulClient;

    public LibraryService(ContentfulClient contentfulClient) {
        this.contentfulClient = contentfulClient;
    }

    List<LibraryLink> fetchLibrary() throws FailedToFetchLibraryException {
        try {
            return contentfulClient.fetchAllLibraryLinks();
        } catch (ContentfulConnectionException exception) {
            throw new FailedToFetchLibraryException("Failed to fetch Library Links", exception);
        }
    }
}
