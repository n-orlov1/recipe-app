package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class RecipeController {
    private final RecipeFinder recipeFinder;

    public RecipeController(RecipeFinder recipeFinder) {
        this.recipeFinder = recipeFinder;
    }
    @GetMapping("/recipe/show/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeFinder.findRecipeById(id));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }
    @GetMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeFinder.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        log.debug(recipeFinder.findCommandById(Long.valueOf(id)).toString());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = recipeFinder.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + savedRecipeCommand.getId();
    }

    @GetMapping("/recipe/delete/{id}")
    public String delete(@PathVariable String id) {
        recipeFinder.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
