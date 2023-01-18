package guru.springframework.controllers;

import guru.springframework.services.IngredientFinder;
import guru.springframework.services.RecipeFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {
    private RecipeFinder recipeFinder;
    private IngredientFinder ingredientFinder;

    public IngredientController(RecipeFinder recipeFinder, IngredientFinder ingredientFinder) {
        this.recipeFinder = recipeFinder;
        this.ingredientFinder = ingredientFinder;
    }

    @GetMapping("/recipe/ingredients/{recipeId}")
    public String getIngredients (@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeFinder.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientFinder.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }
}
