package com.harmelodic.library;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    ContentfulClient contentfulClient;

    @InjectMocks
    LibraryService libraryService;

    @Test
    void fetchLibrarySuccess() throws FailedToFetchLibraryException, ContentfulConnectionException {
        List<LibraryLink> library = List.of(
                new LibraryLink("Some Link thing", "https://example.com", "Example Category", "https://example.com/favicon.ico"),
                new LibraryLink("Another Link thing", "https://another.com", "Example Category", "https://another.com/favicon.ico")
        );
        when(contentfulClient.fetchAllLibraryLinks()).thenReturn(library);

        List<LibraryLink> retrievedLibrary = libraryService.fetchLibrary();

        assertEquals(library, retrievedLibrary);
    }

    @Test
    void fetchLibraryFail() throws ContentfulConnectionException {
        when(contentfulClient.fetchAllLibraryLinks())
                .thenThrow(new ContentfulConnectionException("Failed to fetch LibraryLinks", new Throwable()));

        assertThrows(FailedToFetchLibraryException.class, () -> libraryService.fetchLibrary());
    }
}
