package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import aldora.spring.recipe.model.UnitOfMeasure;
import aldora.spring.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureReactiveRepository unitReactiveRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureServiceImpl unitOfMeasureService;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void findAllCommands() {
        UnitOfMeasure unit1 = new UnitOfMeasure();
        unit1.setId("1");

        UnitOfMeasure unit2 = new UnitOfMeasure();
        unit2.setId("2");

        Flux<UnitOfMeasure> unitOfMeasureFlux = Flux.just(unit1, unit2);

        when(unitReactiveRepository.findAll()).thenReturn(unitOfMeasureFlux);

        List<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAllCommands().collectList().block();

        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitReactiveRepository, times(1)).findAll();
    }
}