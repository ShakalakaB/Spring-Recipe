package aldora.spring.recipe.repositories.reactive;

import aldora.spring.recipe.model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.LinkOption;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    public static final String DESC = "desc";

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
        category.setDescription(DESC);
    }

    @Test
    public void save() {
        Category savedCategory = categoryReactiveRepository.save(category).block();
        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1), count);
        categoryReactiveRepository.deleteById(savedCategory.getId()).block();
    }

    @Test
    public void findByDescription() {
        categoryReactiveRepository.save(category).block();
        Category fetchedCategory = categoryReactiveRepository.findByDescription(DESC).block();
        assertEquals(DESC, fetchedCategory.getDescription());

        categoryReactiveRepository.deleteById(fetchedCategory.getId()).block();
    }
}