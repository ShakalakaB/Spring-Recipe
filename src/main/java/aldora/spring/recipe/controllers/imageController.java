package aldora.spring.recipe.controllers;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.services.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class imageController {
    private final ImageService imageService;

    public imageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(Long.valueOf(recipeId));

        model.addAttribute("recipe", recipeCommand);

        return "recipe/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);

        return "redirect:/recipe/" + recipeId + "/show";
    }
}
