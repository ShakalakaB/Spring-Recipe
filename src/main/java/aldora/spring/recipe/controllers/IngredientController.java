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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @ModelAttribute("unitOfMeasures")
    public Flux<UnitOfMeasureCommand> populateUnitOfMeasures() {
        return unitOfMeasureService.findAllCommands();
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        Mono<RecipeCommand> recipeCommandMono = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommandMono);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        Mono<IngredientCommand> ingredientCommandMono = ingredientService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId);

        model.addAttribute("ingredient", ingredientCommandMono);

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(recipeId).block();

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
//        model.addAttribute("unitOfMeasures", unitOfMeasureService.findAllCommands());

        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        Mono<IngredientCommand> ingredientCommandMono = ingredientService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId);

//        IngredientCommand ingredientCommand = ingredientCommandMono.block();

        model.addAttribute("ingredient", ingredientCommandMono.block());
//        model.addAttribute("unitOfMeasures", unitOfMeasureService.findAllCommands());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand ingredientCommand, Model model) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

//            model.addAttribute("unitOfMeasures", unitOfMeasureService.findAllCommands());
            return "recipe/ingredient/ingredientForm";
        }

        IngredientCommand savedIngredientCommand = ingredientService.saveOrUpdateIngredientCommand(ingredientCommand).block();

        model.addAttribute("ingredient", savedIngredientCommand);

        return  "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        ingredientService.deleteById(ingredientId, recipeId);

        return  "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
