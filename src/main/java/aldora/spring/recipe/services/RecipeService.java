package aldora.spring.recipe.services;

import aldora.spring.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
