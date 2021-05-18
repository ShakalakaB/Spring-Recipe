package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final NotesToNotesCommand notesToNotesCommand;
    private final  IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Override
    @Nullable
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setNotes(notesToNotesCommand.convert(recipe.getNotes()));

        if (recipe.getIngredients() != null) {
            recipe.getIngredients().forEach(ingredient -> {
                recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient));
            });
        }

        if (recipe.getCategories() != null) {
            recipe.getCategories().forEach(category -> {
                recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category));
            });
        }

        return recipeCommand;
    }
}
