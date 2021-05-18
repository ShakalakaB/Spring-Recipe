package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientCommandToIngredientTest {
    public final static Long ID = 1L;
    public final static BigDecimal AMOUNT = BigDecimal.valueOf(2);
    public final static String DESCRIPTION = "de";
    public final static Long UNIT_OF_MEASURE_ID = 1L;

    IngredientCommand ingredientCommand;

    UnitOfMeasureCommand unitOfMeasureCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
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
    void convert() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }

    @Test
    void testNullObject() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(null);
        assertNull(ingredient);
    }

    @Test
    void testEmptyObject() {
        Ingredient ingredient = ingredientCommandToIngredient.convert(new IngredientCommand());
        assertNotNull(ingredient);
    }
}