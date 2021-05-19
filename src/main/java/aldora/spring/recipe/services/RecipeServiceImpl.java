package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("log recipe service");
        Set<Recipe> recipes = new HashSet<>();
         recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

         return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isEmpty()) {
            throw new RuntimeException("Recipe Not Found");
        }

        return optionalRecipe.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }
}
