package aldora.spring.recipe.controllers;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.ImageService;
import aldora.spring.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void ShowImage() throws Exception {
        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipe.setImage(bytesBoxed);

        when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);

    }
}