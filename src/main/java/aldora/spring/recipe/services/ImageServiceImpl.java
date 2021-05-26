package aldora.spring.recipe.services;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile file) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            log.error("recipe is not found. id: " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        try {
            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObject[i] = b;
                i++;
            }

            recipe.setImage(byteObject);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }

    }
}
