package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureServiceImpl unitOfMeasureService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void findAllCommands() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure unit1 = new UnitOfMeasure();
        unit1.setId(1L);

        UnitOfMeasure unit2 = new UnitOfMeasure();
        unit1.setId(2L);

        unitOfMeasures.add(unit1);
        unitOfMeasures.add(unit2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAllCommands();

        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}