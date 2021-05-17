package aldora.spring.recipe.controllers;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {
    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        Set<Recipe> recipesSet = new HashSet<>();
        recipesSet.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        recipesSet.add(recipe);

        ArgumentCaptor<Set<Recipe>> recipeCaptor = ArgumentCaptor.forClass(Set.class);

        when(recipeService.getRecipes()).thenReturn(recipesSet);

        String result = indexController.getIndexPage(model);

        assertEquals("index", result);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), recipeCaptor.capture());

        Set<Recipe> recipesCaptureValue = recipeCaptor.getValue();
        assertEquals(2, recipesCaptureValue.size());
    }
}