package guru.springframework.services;

import guru.springframework.entities.Recipe;

import java.util.List;

public interface RecipeFinder {
    List<Recipe> findRecipes();
}
