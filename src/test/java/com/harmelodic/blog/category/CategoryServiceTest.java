package com.harmelodic.blog.category;

import com.harmelodic.blog.ContentfulBlogClient;
import com.harmelodic.blog.ContentfulBlogConnectionException;
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
    ContentfulBlogClient contentfulBlogClient;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void fetchAllCategoriesSuccess() throws ContentfulBlogConnectionException, FailedToFetchCategoriesException {
        List<Category> categories = List.of(
                new Category("something", "Something"),
                new Category("somethingElse", "Something Else"),
                new Category("finalSomething", "Final Something")
        );

        when(contentfulBlogClient.fetchAllCategories()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.fetchAllCategories();

        assertEquals(categories, retrievedCategories);
    }

    @Test
    void fetchAllCategoriesFail() throws ContentfulBlogConnectionException {
        when(contentfulBlogClient.fetchAllCategories())
                .thenThrow(new ContentfulBlogConnectionException("Failed to fetch Categories", new Throwable()));

        assertThrows(FailedToFetchCategoriesException.class, () -> categoryService.fetchAllCategories());
    }
}
