package guru.springframework.controllers;

import guru.springframework.services.RecipeFinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecipeController {
    private final RecipeFinder recipeFinder;

    public RecipeController(RecipeFinder recipeFinder) {
        this.recipeFinder = recipeFinder;
    }
    @GetMapping("/recipe/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeFinder.findRecipeById(id));
        return "recipe/show";
    }
}
