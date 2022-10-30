package guru.springframework.services;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeFinderImpl implements RecipeFinder {
    private RecipeRepository recipeRepository;

    public RecipeFinderImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public List<Recipe> findRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe ->
                recipes.add(recipe));
        return recipes;
    }
}
