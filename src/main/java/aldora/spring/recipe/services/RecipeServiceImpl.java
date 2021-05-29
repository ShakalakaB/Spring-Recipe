package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.converters.RecipeCommandToRecipe;
import aldora.spring.recipe.converters.RecipeToRecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("log recipe service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        recipeReactiveRepository.deleteById(id);
        return Mono.empty();
    }
}
