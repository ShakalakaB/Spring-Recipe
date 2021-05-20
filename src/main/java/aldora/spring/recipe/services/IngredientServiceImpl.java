package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.converters.IngredientToIngredientCommand;
import aldora.spring.recipe.model.Ingredient;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            //todo error hanle
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if (optionalIngredientCommand.isEmpty()) {
            //todo error hanle
            log.error("Ingredient id not found: " + ingredientId);
        }

        return optionalIngredientCommand.get();
    }

    @Override
    public IngredientCommand updateIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (optionalRecipe.isEmpty()) {
            //todo error hanle
            log.error("recipe id not found. Id: " + ingredientCommand.getRecipeId());
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream().
                filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

        if (optionalIngredient.isEmpty()) {
            //todo error hanle
            log.error("ingredient not found. Id: " + ingredientCommand.getId());
        }

        Ingredient existingIngredient = optionalIngredient.get();
        existingIngredient.setDescription(ingredientCommand.getDescription());
        existingIngredient.setAmount(ingredientCommand.getAmount());
        existingIngredient.setUnitOfMeasure(
                unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("unitOfMeasure Not Found"))
        );

        Recipe savedRecipe = recipeRepository.save(recipe);

        IngredientCommand savedIngredientCommand = ingredientToIngredientCommand.convert(
                savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                        .findFirst().get());

        return savedIngredientCommand;
    }
}
