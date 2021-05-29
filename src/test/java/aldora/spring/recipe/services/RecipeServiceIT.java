package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class RecipeServiceIT {
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

    @Before
    public void setUp() {
        recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
    }

    @Transactional
    @Test
    public void saveRecipeCommand() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand).block();
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    }
}