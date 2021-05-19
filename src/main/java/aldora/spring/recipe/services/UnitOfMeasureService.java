package aldora.spring.recipe.services;

import aldora.spring.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAllCommands();
}
