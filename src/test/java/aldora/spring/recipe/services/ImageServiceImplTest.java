package aldora.spring.recipe.services;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @Captor
    ArgumentCaptor<Recipe> recipeArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        String id = "1";

        MultipartFile image = new MockMultipartFile("imagefile", "test.txt", "text/plain",
                "sping framework guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        imageService.saveImageFile(anyString(), image);

        verify(recipeRepository).save(recipeArgumentCaptor.capture());

        Recipe savedRecipe = recipeArgumentCaptor.getValue();
        assertEquals(id, savedRecipe.getId());
        assertEquals(image.getBytes().length, savedRecipe.getImage().length);
    }
}