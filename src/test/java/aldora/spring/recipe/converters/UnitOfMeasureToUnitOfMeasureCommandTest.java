package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "DE";

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasure unitOfMeasure;

    @Before
    public void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);
    }

    @Test
    public void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
        assertEquals(ID, unitOfMeasureCommand.getId());
    }

    @Test
    public void testNullObject() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(null);
        assertNull(unitOfMeasureCommand);
    }

    @Test
    public void testEmptyObject() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure());
        assertNotNull(unitOfMeasureCommand);
    }
}