package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureFinder {

    Set<UnitOfMeasureCommand> findAllUoms();

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
