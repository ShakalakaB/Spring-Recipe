package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    RecipeCommand recipeCommand;

    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandtoCategory()
        );

        recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
    }

    @Test
    public void convert() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        assertEquals(ID, recipe.getId());
    }

    @Test
    public void testNullObject() {
        Recipe recipe = recipeCommandToRecipe.convert(null);
        assertNull(recipe);
    }

    @Test
    public void testEmptyObject() {
        Recipe recipe = recipeCommandToRecipe.convert(new RecipeCommand());
        assertNotNull(recipe);
    }
}