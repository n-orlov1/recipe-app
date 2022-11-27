package guru.springframework.services;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeFinderImplTest {

    RecipeFinderImpl recipeFinder;
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeFinder = new RecipeFinderImpl(recipeRepository);
    }

    @Test
    public void findRecipes() {
        List<Recipe> recipeData = new ArrayList<>();
        recipeData.add(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipeData);
        List<Recipe> recipeList = recipeFinder.findRecipes();
        assertEquals(1, recipeList.size());
        verify(recipeRepository,times(1)).findAll();
    }
}