package aldora.spring.recipe.services;

import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.exceptions.NotFoundException;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    Recipe savedRecipe;

    @BeforeEach
    void setUp() {
        savedRecipe = new Recipe();
        savedRecipe.setId("1");

        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(savedRecipe));

        Recipe recipe = recipeService.findById(anyString());

        assertNotNull(recipe, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void findByIdNotFound() {
        when(recipeRepository.findById(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            Recipe recipe = recipeService.findById("1");
        });
    }
}