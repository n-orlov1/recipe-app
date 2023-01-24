package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientFinder;
import guru.springframework.services.RecipeFinder;
import guru.springframework.services.UnitOfMeasureFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IngredientController {
    private RecipeFinder recipeFinder;
    private IngredientFinder ingredientFinder;

    private UnitOfMeasureFinder unitOfMeasureFinder;

    public IngredientController(RecipeFinder recipeFinder, IngredientFinder ingredientFinder,
                                UnitOfMeasureFinder unitOfMeasureFinder) {
        this.recipeFinder = recipeFinder;
        this.ingredientFinder = ingredientFinder;
        this.unitOfMeasureFinder = unitOfMeasureFinder;
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

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientFinder.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureFinder.findAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping ("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = unitOfMeasureFinder.saveIngredientCommand(ingredientCommand);

        log.debug("saved recipe id " + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id " + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/"
                + savedIngredientCommand.getId() + "/show";
    }
}
