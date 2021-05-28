package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> findAllCommands();
}
