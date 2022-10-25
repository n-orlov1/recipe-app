package guru.springframework.controllers;

import guru.springframework.entities.Category;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class IndexController {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }
    @GetMapping({"","/","/index","/index.html"})
    public String getIndexPage() {
        Optional<Category> category = categoryRepository.findByDescription("Russian");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("category id is " + category.get().getId());
        System.out.println("uom id is " + unitOfMeasure.get().getId());
        return "index";
    }
}
