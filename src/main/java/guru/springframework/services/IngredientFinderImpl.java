package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.entities.Ingredient;
import guru.springframework.entities.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientFinderImpl implements IngredientFinder {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private IngredientRepository ingredientRepository;
    public IngredientFinderImpl(RecipeRepository recipeRepository,
                                IngredientToIngredientCommand ingredientToIngredientCommand,
                                IngredientCommandToIngredient ingredientCommandToIngredient,
                                UnitOfMeasureRepository unitOfMeasureRepository,
                                IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }
    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()) {
            log.error("Recipe with " + recipeId + "  id was not found");
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if(!ingredientCommand.isPresent()) {
            log.error("Ingredient with " + ingredientId + " id was not found");
        }

        return ingredientCommand.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

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
                Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
                newIngredient.setRecipe(recipe);

                recipe.getIngredients().add(newIngredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedOptionalIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!savedOptionalIngredient.isPresent()) {
                savedOptionalIngredient = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId()
                                .equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedOptionalIngredient.get());
        }
    }

    @Override
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(!optionalRecipe.isPresent()) {
            log.error("Recipe with " + recipeId + " id was not found");
        } else {
            Recipe foundRecipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = foundRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(!optionalIngredient.isPresent()) {
                log.error("Ingredient with " + ingredientId + " id was not found");
            } else {
                Ingredient foundIngredient = optionalIngredient.get();
                Set<Ingredient> ingredients = foundRecipe.getIngredients();
                ingredients.remove(foundIngredient);
                ingredientRepository.deleteById(ingredientId);
                recipeRepository.save(foundRecipe);
            }
        }
    }
}
