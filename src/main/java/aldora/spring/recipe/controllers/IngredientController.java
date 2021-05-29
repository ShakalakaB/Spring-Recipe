package aldora.spring.recipe.controllers;

import aldora.spring.recipe.commands.IngredientCommand;
import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.IngredientService;
import aldora.spring.recipe.services.RecipeService;
import aldora.spring.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand recipe = recipeService.findCommandById(recipeId).block();
        model.addAttribute("recipe", recipe);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId).block();

        model.addAttribute("ingredient", ingredientCommand);

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(recipeId).block();

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("unitOfMeasures", unitOfMeasureService.findAllCommands().collectList().block());

        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId).block();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("unitOfMeasures", unitOfMeasureService.findAllCommands().collectList().block());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand, Model model) {
        IngredientCommand savedIngredientCommand = ingredientService.saveOrUpdateIngredientCommand(ingredientCommand).block();

        model.addAttribute("ingredient", savedIngredientCommand);

        return  "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        ingredientService.deleteById(ingredientId, recipeId).block();

        return  "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
