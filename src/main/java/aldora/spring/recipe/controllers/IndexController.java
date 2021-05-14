package aldora.spring.recipe.controllers;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.CategoryRepository;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import aldora.spring.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
