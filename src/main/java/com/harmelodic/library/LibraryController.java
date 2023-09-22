package com.harmelodic.library;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/library")
@CrossOrigin
public class LibraryController {

    private final LibraryService libraryService;

    LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<LibraryLink> getLibrary() {
        return libraryService.fetchLibrary();
    }
}
