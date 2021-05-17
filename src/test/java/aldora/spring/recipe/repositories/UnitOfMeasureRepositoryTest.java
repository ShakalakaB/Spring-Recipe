package aldora.spring.recipe.repositories;

import aldora.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByDescription() {
        String unit = "Teaspoon";
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(unit);
        assertEquals(unit, unitOfMeasure.get().getDescription());
    }

    @Test
    void findByDescription2() {
        String unit = "Tablespoon";
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(unit);
        assertEquals(unit, unitOfMeasure.get().getDescription());
    }
}