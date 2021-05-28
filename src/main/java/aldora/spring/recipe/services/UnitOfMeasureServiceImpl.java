package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import aldora.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import aldora.spring.recipe.repositories.UnitOfMeasureRepository;
import aldora.spring.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureReactiveRepository unitReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitReactiveRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitReactiveRepository = unitReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> findAllCommands() {
        return unitReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
