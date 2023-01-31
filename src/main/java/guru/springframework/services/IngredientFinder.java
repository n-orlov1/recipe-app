package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientFinder {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(Long recipeId, Long ingredientId);
}
