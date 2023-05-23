package com.harmelodic.blog.post;

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
    PostRepository postRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void fetchAllCategoriesSuccess() {
        List<String> categories = List.of(
                "Something",
                "Something Else",
                "Final Something"
        );

        when(postRepository.fetchAllCategories()).thenReturn(categories);

        List<String> retrievedCategories = categoryService.fetchAllCategories();

        assertEquals(categories, retrievedCategories);
    }

    @Test
    void fetchAllCategoriesFail() {
        when(postRepository.fetchAllCategories()).thenThrow(new RuntimeException("Failed to fetch Accounts"));

        assertThrows(RuntimeException.class, () -> categoryService.fetchAllCategories());
    }
}
