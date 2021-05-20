package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long ingredientId, Long recipeId);
}
