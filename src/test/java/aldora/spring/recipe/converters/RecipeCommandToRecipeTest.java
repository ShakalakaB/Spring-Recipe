package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
    public final static Long ID = 1L;
    public final static String DESCRIPTION = "DE";

    RecipeCommand recipeCommand;

    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
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
    void convert() {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        assertEquals(ID, recipe.getId());
    }

    @Test
    void testNullObject() {
        Recipe recipe = recipeCommandToRecipe.convert(null);
        assertNull(recipe);
    }

    @Test
    void testEmptyObject() {
        Recipe recipe = recipeCommandToRecipe.convert(new RecipeCommand());
        assertNotNull(recipe);
    }
}