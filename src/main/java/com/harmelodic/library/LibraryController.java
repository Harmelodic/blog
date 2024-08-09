package com.harmelodic.library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/library")
@CrossOrigin
class LibraryController {

    private final LibraryService libraryService;

    LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    record LibraryLinkV1(String title,
                         String href,
                         String category,
                         String favicon) {
    }

    @GetMapping
    List<LibraryLinkV1> getLibrary() {
        try {
            return libraryService.fetchLibrary()
                    .stream()
                    .map(libraryLink ->
                            new LibraryLinkV1(libraryLink.title(),
                                    libraryLink.href(),
                                    libraryLink.category(),
                                    libraryLink.favicon()))
                    .toList();
        } catch (LibraryService.FailedToFetchLibraryException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch Library", exception);
        }
    }
}
