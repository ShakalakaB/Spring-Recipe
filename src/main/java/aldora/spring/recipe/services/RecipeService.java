package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);
}
