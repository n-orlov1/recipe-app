package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.Ingredient;
import guru.springframework.entities.Recipe;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
@Slf4j
public class UnitOfMeasureFinderImpl implements UnitOfMeasureFinder {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private RecipeRepository recipeRepository;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    public UnitOfMeasureFinderImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                   UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand,
                                   RecipeRepository recipeRepository,
                                   IngredientCommandToIngredient ingredientCommandToIngredient,
                                   IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }
    @Override
    public Set<UnitOfMeasureCommand> findAllUoms() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(uom -> unitOfMeasureToUnitOfMeasureCommand.convert(uom))
                .collect(Collectors.toSet());
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        System.out.println(ingredientCommand);

        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository
                .findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("Unit of measure with "
                        + ingredientCommand.getUnitOfMeasure().getId() + " id was not found"));
        System.out.println("everything is fine " + unitOfMeasure);

        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!optionalRecipe.isPresent()) {
            log.error("Recipe with " + ingredientCommand.getRecipeId() + " id was not found");
            return new IngredientCommand();
        } else {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (optionalIngredient.isPresent()) {
                Ingredient foundIngredient = optionalIngredient.get();
                foundIngredient.setDescription(ingredientCommand.getDescription());
                foundIngredient.setAmount(ingredientCommand.getAmount());
                foundIngredient.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("Unit of measure with "
                                + ingredientCommand.getUnitOfMeasure().getId() + " id was not found")));
            } else {
                recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }
    }

}
