package com.harmelodic.blog.category;

import com.harmelodic.blog.BlogContentfulClient;
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
class CategoryServiceTest {

    @Mock
    BlogContentfulClient blogContentfulClient;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void fetchAllCategoriesSuccess() {
        List<Category> categories = List.of(
                new Category("something", "Something"),
                new Category("somethingElse", "Something Else"),
                new Category("finalSomething", "Final Something")
        );

        when(blogContentfulClient.fetchAllCategories()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.fetchAllCategories();

        assertEquals(categories, retrievedCategories);
    }

    @Test
    void fetchAllCategoriesFail() {
        when(blogContentfulClient.fetchAllCategories()).thenThrow(new RuntimeException("Failed to fetch Categories"));

        assertThrows(RuntimeException.class, () -> categoryService.fetchAllCategories());
    }
}
