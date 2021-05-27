package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    Recipe recipe;

    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() {
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
    public void convert() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        assertEquals(ID, recipeCommand.getId());
    }

    @Test
    public void testNullObject() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(null);
        assertNull(recipeCommand);
    }

    @Test
    public void testEmptyObject() {
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(new Recipe());
        assertNotNull(recipeCommand);
    }
}