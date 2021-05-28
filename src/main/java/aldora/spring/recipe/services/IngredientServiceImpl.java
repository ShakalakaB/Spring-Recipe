package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.converters.IngredientCommandToIngredient;
import aldora.spring.recipe.converters.IngredientToIngredientCommand;
import aldora.spring.recipe.exceptions.NotFoundException;
import aldora.spring.recipe.model.Ingredient;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            log.error("recipe id not found. Id: " + recipeId);
            throw new NotFoundException("Recipe Not Found. recipe id: " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if (optionalIngredientCommand.isEmpty()) {
            log.error("Ingredient id not found: " + ingredientId);
            throw new NotFoundException("Ingredient Not Found. ingredient id: " + ingredientId);
        }

        IngredientCommand ingredientCommand = optionalIngredientCommand.get();
        ingredientCommand.setRecipeId(recipeId);

        return optionalIngredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (optionalRecipe.isEmpty()) {
            log.error("recipe id not found. Id: " + ingredientCommand.getRecipeId());
            throw new NotFoundException("Recipe Not Found. recipe id: " + ingredientCommand.getRecipeId());
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

        Recipe savedRecipe = recipeRepository.save(recipe);

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
                    .orElseThrow(() -> new RuntimeException("unitOfMeasure Not Found")
            ));
        }

        return ingredientToIngredientCommand.convert(savedOptionalIngredient.get());
    }

    @Override
    public void deleteById(String ingredientId, String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            log.error("recipe is not found. Id: " + recipeId);
            throw new NotFoundException("Recipe Not Found");
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream().
                filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (optionalIngredient.isEmpty()) {
            log.error("ingredient is not found. Id: " + ingredientId);
            throw new NotFoundException("Ingredient Not Found");
        }

        Ingredient ingredient = optionalIngredient.get();

        recipe.getIngredients().remove(ingredient);

        recipeRepository.save(recipe);
    }
}
