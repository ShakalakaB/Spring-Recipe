package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.converters.IngredientCommandToIngredient;
import aldora.spring.recipe.converters.IngredientToIngredientCommand;
import aldora.spring.recipe.exceptions.NotFoundException;
import aldora.spring.recipe.model.Ingredient;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import aldora.spring.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(recipeId);

        Mono<IngredientCommand> ingredientCommandMono = monoRecipe.map(
                recipe -> recipe.getIngredients().stream().filter(
                        ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)).findFirst())
                .filter(optionalIngredient -> optionalIngredient.isPresent())
                .map(optionalIngredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(optionalIngredient.get());
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });

        return ingredientCommandMono;
    }

    @Override
    public Mono<IngredientCommand> saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (optionalRecipe.isEmpty()) {
            log.error("recipe id not found. Id: " + ingredientCommand.getRecipeId());
            return Mono.just(new IngredientCommand());
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream().
                filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

        Ingredient detachedIngredient = null;
        if (optionalIngredient.isEmpty()) {
            log.error("ingredient not found. Id: " + ingredientCommand.getId());

            detachedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
            recipe.addIngredient(detachedIngredient);
        } else {
            Ingredient existingIngredient = optionalIngredient.get();
            existingIngredient.setDescription(ingredientCommand.getDescription());
            existingIngredient.setAmount(ingredientCommand.getAmount());
            existingIngredient.setUnitOfMeasure(
                    unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("unitOfMeasure Not Found"))
            );
        }

        Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

        Optional<Ingredient> savedOptionalIngredient = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (savedOptionalIngredient.isEmpty()) {
            Ingredient finalDetachedIngredient = detachedIngredient;

            savedOptionalIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(finalDetachedIngredient.getId()))
                    .findFirst();

            Ingredient savedIngredient = savedOptionalIngredient.get();

            savedIngredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("unitOfMeasure Not Found")));
        }

        IngredientCommand savedIngredientCommand = ingredientToIngredientCommand.convert(savedOptionalIngredient.get());
        savedIngredientCommand.setRecipeId(recipe.getId());

        return Mono.just(savedIngredientCommand);
    }

    @Override
    public Mono<Void> deleteById(String ingredientId, String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            log.error("recipe is not found. Id: " + recipeId);
            return Mono.empty();
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream().
                filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (optionalIngredient.isEmpty()) {
            log.error("ingredient is not found. Id: " + ingredientId);
            return Mono.empty();
        }

        Ingredient ingredient = optionalIngredient.get();

        recipe.getIngredients().remove(ingredient);

        recipeRepository.save(recipe);
        return Mono.empty();
    }
}
