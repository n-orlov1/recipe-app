package guru.springframework.services;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeFinderImpl implements RecipeFinder {
    private RecipeRepository recipeRepository;

    public RecipeFinderImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public List<Recipe> findRecipes() {
        log.debug("Find recipes method was called!");
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe ->
                recipes.add(recipe));
        return recipes;
    }

    @Override
    public Recipe findRecipeById(Long id) {
       log.debug("Find recipe by id method was called!");
       Optional<Recipe> recipe = recipeRepository.findById(id);

       if(recipe == null) {
           throw new RuntimeException("Recipe with this id doesn't exist");
       }

       return recipe.get();
    }
}
