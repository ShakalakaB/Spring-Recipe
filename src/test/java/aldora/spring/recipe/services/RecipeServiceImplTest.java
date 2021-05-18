package aldora.spring.recipe.services;

import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        savedRecipe.setId(1L);

        MockitoAnnotations.openMocks(this);

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
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(savedRecipe));

        Recipe recipe = recipeService.findById(anyLong());

        assertNotNull(recipe, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}