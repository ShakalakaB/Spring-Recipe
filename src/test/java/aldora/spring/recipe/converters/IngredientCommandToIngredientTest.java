package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    public final static String ID = "1";
    public final static BigDecimal AMOUNT = BigDecimal.valueOf(2);
    public final static String DESCRIPTION = "de";
    public final static String UNIT_OF_MEASURE_ID = "1";

    IngredientCommand ingredientCommand;

    UnitOfMeasureCommand unitOfMeasureCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UNIT_OF_MEASURE_ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
    }

    @Test
    public void convert() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }

    @Test
    public void testNullObject() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(null);
        assertNull(ingredient);
    }

    @Test
    public void testEmptyObject() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(new IngredientCommand());
        assertNotNull(ingredient);
    }
}