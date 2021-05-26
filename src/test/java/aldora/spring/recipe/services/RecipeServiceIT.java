package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class RecipeServiceIT {
    public static final String ID = "1";
    public static final String  NEW_DESCRIPTION = "description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    RecipeCommand recipeCommand;

    @BeforeEach
    void setUp() {
        recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
    }

    @Transactional
    @Test
    void saveRecipeCommand() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    }
}