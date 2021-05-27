package aldora.spring.recipe.controllers;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {
        Set<Recipe> recipesSet = new HashSet<>();
        recipesSet.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId("1");

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