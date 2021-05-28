package aldora.spring.recipe.repositories.reactive;

import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
    public static final String DESC = "unit";
    @Autowired
    UnitOfMeasureReactiveRepository unitReactiveRepository;

    UnitOfMeasure unitOfMeasure;

    @Before
    public void setUp() throws Exception {
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(DESC);
    }

    @Test
    public void save() {
        UnitOfMeasure savedUnit = unitReactiveRepository.save(unitOfMeasure).block();

        assertEquals(DESC, savedUnit.getDescription());

        unitReactiveRepository.deleteById(savedUnit.getId()).block();
    }

    @Test
    public void findByDescription() {
        UnitOfMeasure savedUnit = unitReactiveRepository.save(unitOfMeasure).block();
        UnitOfMeasure fetchedUnit = unitReactiveRepository.findByDescription(DESC).block();

        assertEquals(DESC, fetchedUnit.getDescription());

        unitReactiveRepository.deleteById(savedUnit.getId()).block();
    }
}