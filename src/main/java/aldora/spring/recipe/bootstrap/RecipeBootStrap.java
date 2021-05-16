package aldora.spring.recipe.bootstrap;

import aldora.spring.recipe.model.*;
import aldora.spring.recipe.repositories.CategoryRepository;
import aldora.spring.recipe.repositories.RecipeRepository;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public RecipeBootStrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                           CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("inside recipe bootstrap");
    }

    private Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        Optional<UnitOfMeasure> optionalEachUnit = unitOfMeasureRepository.findByDescription("Each");

        if (optionalEachUnit.isEmpty()) {
            throw new RuntimeException("Expected UOM 'Each' Not Found");
        }

        UnitOfMeasure eachUnit = optionalEachUnit.get();

        Optional<UnitOfMeasure> optionalTeaspoonUnit = unitOfMeasureRepository.findByDescription("Teaspoon");

        if (optionalTeaspoonUnit.isEmpty()) {
            throw new RuntimeException("Expected UOM 'Teaspoon' Not Found");
        }

        UnitOfMeasure teaspoonUnit = optionalTeaspoonUnit.get();

        Optional<Category> optionalAmericanCategory = categoryRepository.findByDescription("American");

        if (optionalAmericanCategory.isEmpty()) {
            throw new RuntimeException("Expected Category 'American' Not Found");
        }

        Category americanCategory = optionalAmericanCategory.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(10);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
//        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUnit));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal("0.5"), teaspoonUnit));

        guacRecipe.getCategories().add(americanCategory);

        recipes.add(guacRecipe);

        return  recipes;
    }
}
