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
    LibraryContentfulClient libraryContentfulClient;

    @InjectMocks
    LibraryService libraryService;

    @Test
    void fetchLibrarySuccess() {
        List<LibraryLink> library = List.of(
                new LibraryLink("Some Link thing", "https://example.com", "Example Category", "https://example.com/favicon.ico"),
                new LibraryLink("Another Link thing", "https://another.com", "Example Category", "https://another.com/favicon.ico")
        );
        when(libraryContentfulClient.fetchAllLibraryLinks()).thenReturn(library);

        List<LibraryLink> retrievedLibrary = libraryService.fetchLibrary();

        assertEquals(library, retrievedLibrary);
    }

    @Test
    void fetchLibraryFail() {
        when(libraryContentfulClient.fetchAllLibraryLinks()).thenThrow(new RuntimeException("Failed to fetch LibraryLinks"));

        assertThrows(RuntimeException.class, () -> libraryService.fetchLibrary());
    }
}
