package com.harmelodic.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
@CrossOrigin
class CategoryController {

    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    record CategoryV1(String id,
                      String name) {
    }

    @GetMapping
    List<CategoryV1> getAllCategories() {
        try {
            return categoryService.fetchAllCategories()
                    .stream()
                    .map(category -> new CategoryV1(category.id(), category.name()))
                    .toList();
        } catch (CategoryService.FailedToFetchCategoriesException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch Categories.", exception);
        }
    }
}
