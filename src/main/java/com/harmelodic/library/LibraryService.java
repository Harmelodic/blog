package com.harmelodic.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryContentfulClient libraryContentfulClient;

    public LibraryService(LibraryContentfulClient libraryContentfulClient) {
        this.libraryContentfulClient = libraryContentfulClient;
    }

    List<LibraryLink> fetchLibrary() throws FailedToFetchLibraryException {
        return libraryContentfulClient.fetchAllLibraryLinks();
    }
}
