package guru.springframework.controllers;

import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeFinderImpl;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    public void mockMvc() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @SneakyThrows
    @Test
    public void getIndexPage() {
        //given
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe());

        when(recipeFinder.findRecipes()).thenReturn(recipeList);

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        assertEquals("index", indexController.getIndexPage(model));
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeFinder, times(1)).findRecipes();

        List<Recipe> recipes = argumentCaptor.getValue();
        assertEquals(1, recipes.size());
    }
}