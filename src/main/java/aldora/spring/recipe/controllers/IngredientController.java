package aldora.spring.recipe.controllers;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipe);

        return "recipe/ingredient/list";
    }
}
