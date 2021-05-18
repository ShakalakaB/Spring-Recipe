package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.model.Ingredient;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {
    public final static Long ID = 1L;
    public final static BigDecimal AMOUNT = BigDecimal.valueOf(2);
    public final static String DESCRIPTION = "de";
    public final static Long UNIT_OF_MEASURE_ID = 1L;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        ingredient.setUnitOfMeasure(unitOfMeasure);
    }

    @Test
    void convert() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        assertEquals(ID, ingredientCommand.getId());
    }

    @Test
    void testNullObject() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(null);
        assertNull(ingredientCommand);
    }

    @Test
    void testEmptyObject() {
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(new Ingredient());
        assertNotNull(ingredientCommand);
    }
}