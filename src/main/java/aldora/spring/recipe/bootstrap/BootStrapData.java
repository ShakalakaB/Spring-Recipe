package aldora.spring.recipe.bootstrap;

import aldora.spring.recipe.model.*;
import aldora.spring.recipe.repositories.CategoryRepository;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public BootStrapData(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                         CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setDescription("How to Make Perfect Guacamole");
        recipe1.setPrepTime(10);
        recipe1.setCookTime(10);
        recipe1.setServings(4);
        recipe1.setSource("source1");
        recipe1.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe1.setDirections("directions1");
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setImage(getImageByte("src/main/resources/static/images/Guacamole.webp"));

        Notes notes1 = new Notes();
        notes1.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hour");
        notes1.setRecipe(recipe1);
        recipe1.setNotes(notes1);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(new BigDecimal(0.25));
        ingredient1.setDescription("avocados");
        ingredient1.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("teaspoon").get());
        ingredient1.setRecipe(recipe1);
        recipe1.getIngredients().add(ingredient1);

        Category mexicanCategory = categoryRepository.findByDescription("Mexican").get();
//        Category mexicanCategory = new Category();
//        mexicanCategory.setDescription("mexican");
//        mexicanCategory.getRecipe().add(recipe1);
//        categoryRepository.save(mexicanCategory);

        recipe1.getCategories().add(mexicanCategory);

        recipeRepository.save(recipe1);

    }

    private Byte[] getImageByte(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        Byte[] imageBytes = new Byte[bytes.length];

        int i = 0;

        for (byte b : bytes) {
            imageBytes[i++] = b;
        }

        return imageBytes;
    }
}
