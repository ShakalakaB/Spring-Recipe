package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "DE";

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    UnitOfMeasureCommand unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);
    }

    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        assertEquals(ID, unitOfMeasure.getId());
    }

    @Test
    void testNullObject() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(null);
        assertNull(unitOfMeasure);
    }

    @Test
    void testEmptyObject() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand());
        assertNotNull(unitOfMeasure);
    }
}