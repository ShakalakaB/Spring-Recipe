package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.CategoryCommand;
import aldora.spring.recipe.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandtoCategoryTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "ca";
    CategoryCommand categoryCommand;

    CategoryCommandtoCategory categoryCommandtoCategory;

    @Before
    public void setUp() {
        categoryCommandtoCategory = new CategoryCommandtoCategory();

        categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);
    }

    @Test
    public void convert() {
        Category category = categoryCommandtoCategory.convert(categoryCommand);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testNullObject() {
        Category category = categoryCommandtoCategory.convert(null);
        assertNull(category);
    }

    @Test
    public void testEmptyObject() {
        Category category = categoryCommandtoCategory.convert(new CategoryCommand());
        assertNotNull(category);
    }
}