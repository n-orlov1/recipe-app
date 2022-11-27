package guru.springframework.controllers;

import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeFinderImpl;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IndexControllerTest {
    IndexController indexController;
    @Mock
    RecipeFinderImpl recipeFinder;
    @Mock
    Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeFinder);
    }

    @SneakyThrows
    @Test
    public void getIndexPage() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe());
        when(recipeFinder.findRecipes()).thenReturn(recipeList);

        assertEquals("index", indexController.getIndexPage(model));
        verify(model, times(1)).addAttribute("recipes", recipeList);
        verify(recipeFinder, times(1)).findRecipes();
    }
}