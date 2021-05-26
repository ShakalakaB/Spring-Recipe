package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(String ingredientId, String recipeId);
}
