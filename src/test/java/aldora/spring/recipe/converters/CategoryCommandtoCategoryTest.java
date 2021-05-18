package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.CategoryCommand;
import aldora.spring.recipe.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandtoCategoryTest {
    public static final long ID = 1L;
    public static final String DESCRIPTION = "ca";
    CategoryCommand categoryCommand;

    CategoryCommandtoCategory categoryCommandtoCategory;

    @BeforeEach
    void setUp() {
        categoryCommandtoCategory = new CategoryCommandtoCategory();

        categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);
    }

    @Test
    void convert() {
        Category category = categoryCommandtoCategory.convert(categoryCommand);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    void testNullObject() {
        Category category = categoryCommandtoCategory.convert(null);
        assertNull(category);
    }

    @Test
    void testEmptyObject() {
        Category category = categoryCommandtoCategory.convert(new CategoryCommand());
        assertNotNull(category);
    }
}