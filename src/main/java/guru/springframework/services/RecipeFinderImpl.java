package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeFinderImpl implements RecipeFinder {
    private RecipeRepository recipeRepository;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeFinderImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                            RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
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
    @Transactional
    public Recipe findRecipeById(Long id) {
       log.debug("Find recipe by id method was called!");
       Optional<Recipe> recipe = recipeRepository.findById(id);

       if(recipe == null) {
           throw new RuntimeException("Recipe with this id doesn't exist");
       }

       return recipe.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        Recipe recipe = findRecipeById(id);
       // System.out.println("size " + recipe.getIngredients().size());

//        recipe.getIngredients().stream()
//                .forEach(i -> {
//                    System.out.println("ingredient description " + i.getDescription());
//                });

        RecipeCommand convertedRecipe = recipeToRecipeCommand.convert(recipe);

        convertedRecipe.getIngredients().forEach(System.out::println);

        return convertedRecipe;
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
