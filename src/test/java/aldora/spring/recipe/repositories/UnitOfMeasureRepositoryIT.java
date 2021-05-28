package aldora.spring.recipe.repositories;

import aldora.spring.recipe.bootstrap.RecipeBootStrap;
import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        RecipeBootStrap recipeBootStrap = new RecipeBootStrap(recipeRepository, unitOfMeasureRepository, categoryRepository);
        recipeBootStrap.onApplicationEvent(null);
    }

    @Test
    public void findByDescription() {
        String unit = "Teaspoon";
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(unit);
        assertEquals(unit, unitOfMeasure.get().getDescription());
    }

    @Test
    public void findByDescription2() {
        String unit = "Tablespoon";
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(unit);
        assertEquals(unit, unitOfMeasure.get().getDescription());
    }
}