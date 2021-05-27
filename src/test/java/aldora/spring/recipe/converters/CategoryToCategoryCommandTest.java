package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.CategoryCommand;
import aldora.spring.recipe.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "DE";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @Before
    public void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void convert() {
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }

    @Test
    public void testNullObject() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(null);
        assertNull(categoryCommand);
    }

    @Test
    public void testEmptyObject() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(new Category());
        assertNotNull(categoryCommand);
    }
}