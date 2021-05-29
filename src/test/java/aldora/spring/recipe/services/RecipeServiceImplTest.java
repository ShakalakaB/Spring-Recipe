package aldora.spring.recipe.services;

import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.exceptions.NotFoundException;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    Recipe savedRecipe;

    @Before
    public void setUp() {
        savedRecipe = new Recipe();
        savedRecipe.setId("1");

        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());

        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(new Recipe()));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(1, recipes.size());

        verify(recipeReactiveRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(savedRecipe));

        Recipe recipe = recipeService.findById(anyString()).block();

        assertNotNull("Null recipe returned", recipe);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Ignore
    @Test(expected = NotFoundException.class)
    public void findByIdNotFound() {
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.empty());

        Recipe recipe = recipeService.findById("1").block();
    }
}