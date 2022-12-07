package guru.springframework.services;

import guru.springframework.entities.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeFinder {
    List<Recipe> findRecipes();
    Recipe findRecipeById(Long id);
}
