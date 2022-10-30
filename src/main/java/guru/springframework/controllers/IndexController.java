package guru.springframework.controllers;

import guru.springframework.entities.Category;
import guru.springframework.entities.Recipe;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeFinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeFinder recipeFinder;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeFinder recipeFinder) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeFinder = recipeFinder;
    }
    @GetMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model) throws IOException {
        /*Optional<Category> category = categoryRepository.findByDescription("Russian");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("category id is " + category.get().getId());
        System.out.println("uom id is " + unitOfMeasure.get().getId());*/
        model.addAttribute("recipes", recipeFinder.findRecipes());
        return "index";
    }
}
