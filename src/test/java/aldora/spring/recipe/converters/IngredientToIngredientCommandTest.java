package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.model.Ingredient;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    public final static String ID = "1";
    public final static BigDecimal AMOUNT = BigDecimal.valueOf(2);
    public final static String DESCRIPTION = "de";
    public final static String UNIT_OF_MEASURE_ID = "1";

    IngredientToIngredientCommand ingredientToIngredientCommand;

    Ingredient ingredient;

    @Before
    public void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        ingredient.setUnitOfMeasure(unitOfMeasure);
    }

    @Test
    public void convert() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        assertEquals(ID, ingredientCommand.getId());
    }

    @Test
    public void testNullObject() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(null);
        assertNull(ingredientCommand);
    }

    @Test
    public void testEmptyObject() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(new Ingredient());
        assertNotNull(ingredientCommand);
    }
}