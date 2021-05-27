package aldora.spring.recipe.controllers;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.exceptions.NotFoundException;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

class RecipeControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    RecipeController recipeController;

    Recipe savedRecipe;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);

        savedRecipe = new Recipe();
        savedRecipe.setId("1");

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void showById() throws Exception {
        when(recipeService.findById(anyString())).thenReturn(savedRecipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testSaveOrUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));
    }

    @Test
    void testSaveOrUpdateRecipeValidationFail() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("!");

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("!");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void updateRecipeNotFound() throws Exception {
        when(recipeService.findCommandById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/404"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyString());
    }
}