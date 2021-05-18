package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.CategoryCommand;
import aldora.spring.recipe.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandtoCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Override
    @Nullable
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setDescription(categoryCommand.getDescription());
        return category;
    }
}
