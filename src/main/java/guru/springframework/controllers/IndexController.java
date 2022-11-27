package guru.springframework.controllers;

import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
@Slf4j
public class IndexController {

    private final RecipeFinder recipeFinder;
    public IndexController(RecipeFinder recipeFinder) {
        this.recipeFinder = recipeFinder;
    }
    @GetMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model) throws IOException {
        log.debug("Get index page method was called!");
        model.addAttribute("recipes", recipeFinder.findRecipes());
        return "index";
    }
}
