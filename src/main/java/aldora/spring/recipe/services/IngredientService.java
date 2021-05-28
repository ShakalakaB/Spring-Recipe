package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Void> deleteById(String ingredientId, String recipeId);
}
