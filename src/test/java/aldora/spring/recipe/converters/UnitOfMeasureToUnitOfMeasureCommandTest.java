package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final Long ID = 1L;
    public static final String DESCRIPTION = "DE";

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);
    }

    @Test
    void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
        assertEquals(ID, unitOfMeasureCommand.getId());
    }

    @Test
    void testNullObject() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(null);
        assertNull(unitOfMeasureCommand);
    }

    @Test
    void testEmptyObject() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure());
        assertNotNull(unitOfMeasureCommand);
    }
}