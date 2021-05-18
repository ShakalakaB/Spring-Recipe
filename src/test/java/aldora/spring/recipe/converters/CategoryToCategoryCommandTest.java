package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.CategoryCommand;
import aldora.spring.recipe.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    public static final Long ID = 1L;
    public static final String DESCRIPTION = "DE";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }

    @Test
    void testNullObject() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(null);
        assertNull(categoryCommand);
    }

    @Test
    void testEmptyObject() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(new Category());
        assertNotNull(categoryCommand);
    }
}