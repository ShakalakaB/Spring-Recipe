package aldora.spring.recipe.repositories.reactive;

import aldora.spring.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
    public static final String DESC = "recipe";

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    Recipe recipe;

    @Before
    public void setUp() throws Exception {
        recipe = new Recipe();
        recipe.setDescription(DESC);
    }

    @Test
    public void save() {
        Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

        assertEquals(Long.valueOf(1), recipeReactiveRepository.count().block());
        assertEquals(DESC, savedRecipe.getDescription());

        recipeReactiveRepository.deleteById(recipe.getId()).block();
    }
}