package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "DE";

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    UnitOfMeasureCommand unitOfMeasureCommand;

    @Before
    public void setUp() {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);
    }

    @Test
    public void convert() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        assertEquals(ID, unitOfMeasure.getId());
    }

    @Test
    public void testNullObject() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(null);
        assertNull(unitOfMeasure);
    }

    @Test
    public void testEmptyObject() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand());
        assertNotNull(unitOfMeasure);
    }
}