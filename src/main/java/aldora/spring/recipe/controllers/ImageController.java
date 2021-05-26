package aldora.spring.recipe.controllers;

import aldora.spring.recipe.commands.RecipeCommand;
import aldora.spring.recipe.model.Recipe;
import aldora.spring.recipe.services.ImageService;
import aldora.spring.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        model.addAttribute("recipe", recipeCommand);

        return "recipe/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void showImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.findById(recipeId);

        if (recipe.getImage() != null) {
            byte[] byteObject = new byte[recipe.getImage().length];
            int i = 0;

            for (byte b : recipe.getImage()) {
                byteObject[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteObject);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
