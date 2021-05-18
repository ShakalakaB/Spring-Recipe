package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final NotesCommandToNotes notesCommandToNotes;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandtoCategory categoryCommandtoCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 CategoryCommandtoCategory categoryCommandtoCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandtoCategory = categoryCommandtoCategory;
    }


    @Synchronized
    @Override
    @Nullable
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setNotes(notesCommandToNotes.convert(recipeCommand.getNotes()));

        if (recipeCommand.getIngredients() != null) {
            recipeCommand.getIngredients().forEach(
                    ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand))
            );
        }

        if (recipeCommand.getCategories() != null) {
            recipeCommand.getCategories().forEach(
                    categoryCommand -> recipe.getCategories().add(categoryCommandtoCategory.convert(categoryCommand))
            );
        }

        return  recipe;
    }
}
