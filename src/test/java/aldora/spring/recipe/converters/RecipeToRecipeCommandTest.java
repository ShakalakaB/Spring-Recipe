package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    Recipe recipe;

    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        recipeToRecipeCommand = new RecipeToRecipeCommand(
                new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand()
        );

        recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
    }

    @Test
    void convert() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        assertEquals(ID, recipeCommand.getId());
    }

    @Test
    void testNullObject() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(null);
        assertNull(recipeCommand);
    }

    @Test
    void testEmptyObject() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(new Recipe());
        assertNotNull(recipeCommand);
    }
}