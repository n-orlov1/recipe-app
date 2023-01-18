package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientFinder {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
