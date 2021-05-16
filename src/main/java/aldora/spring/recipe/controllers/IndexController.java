package aldora.spring.recipe.controllers;

import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.repositories.CategoryRepository;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import aldora.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("inside index controller");
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
