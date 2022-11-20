package guru.springframework.bootstrap;

import guru.springframework.entities.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Component
@Slf4j
public class RecipesLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipesLoader(UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("On application event method was called !");
        recipeRepository.save(getRecipe());
    }

    public Recipe getRecipe() throws Exception {
        log.debug("Run method was called!");
        //Guacamole
        Recipe guacamole = new Recipe();

        Category americanCategory = categoryRepository.findByDescription("American").orElseThrow();
        Category mexicanCategoty = categoryRepository.findByDescription("Mexican").orElseThrow();

        UnitOfMeasure piece = unitOfMeasureRepository.findByDescription("Piece").orElseThrow();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").orElseThrow();
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").orElseThrow();
        UnitOfMeasure pinch = unitOfMeasureRepository.findByDescription("Pinch").orElseThrow();
        UnitOfMeasure slice = unitOfMeasureRepository.findByDescription("Slice").orElseThrow();

        Ingredient avocado = new Ingredient();
        avocado.setUnitOfMeasure(piece);
        avocado.setDescription("Avocado");
        avocado.setAmount(BigDecimal.valueOf(2));

        Ingredient kosherSalt = new Ingredient();
        kosherSalt.setUnitOfMeasure(teaspoon);
        kosherSalt.setDescription("Kosher salt");
        kosherSalt.setAmount(BigDecimal.valueOf(0.25));

        Ingredient lemonJuice = new Ingredient();
        lemonJuice.setUnitOfMeasure(tablespoon);
        lemonJuice.setDescription("Lemon juice");
        lemonJuice.setAmount(BigDecimal.valueOf(1));

        Ingredient mincedRedOnion = new Ingredient();
        mincedRedOnion.setUnitOfMeasure(tablespoon);
        mincedRedOnion.setDescription("Minced red onion");
        mincedRedOnion.setAmount(BigDecimal.valueOf(3));

        Ingredient serranoChilis = new Ingredient();
        serranoChilis.setUnitOfMeasure(piece);
        serranoChilis.setDescription("Serrano chili");
        serranoChilis.setAmount(BigDecimal.valueOf(1));

        Ingredient cilantro = new Ingredient();
        cilantro.setUnitOfMeasure(tablespoon);
        cilantro.setDescription("Cilantro");
        cilantro.setAmount(BigDecimal.valueOf(2));

        Ingredient freshlyGroundBlackPepper = new Ingredient();
        freshlyGroundBlackPepper.setUnitOfMeasure(pinch);
        freshlyGroundBlackPepper.setDescription("Freshly ground black pepper");
        freshlyGroundBlackPepper.setAmount(BigDecimal.valueOf(1));

        Ingredient ripeTomato = new Ingredient();
        ripeTomato.setUnitOfMeasure(piece);
        ripeTomato.setDescription("Ripe tomato");
        ripeTomato.setAmount(BigDecimal.valueOf(0.5));

        Ingredient redRadish = new Ingredient();
        redRadish.setUnitOfMeasure(slice);
        redRadish.setDescription("Red radish");
        redRadish.setAmount(BigDecimal.valueOf(10));

        Ingredient tortillaChips = new Ingredient();
        tortillaChips.setUnitOfMeasure(piece);
        tortillaChips.setDescription("Tortilla chips");
        tortillaChips.setAmount(BigDecimal.valueOf(10));

        Note guacamoleNotes = new Note();
        guacamoleNotes.setRecipeNotes(" Ingredients for Easy Guacamole\n" +
                "\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help balance the richness of the avocado. If you want, add chopped cilantro, chilis, onion, and/or tomato. " +
                " How To Pick Perfectly Ripe Avocados\n" +
                "\n" +
                "The trick to making perfect guacamole is using avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and flavorless. Too ripe and the taste will be off. ");

        Path file = Paths.get("/home/nik/myprojects/recipe-app/src/main/resources/guacomole.png");
        byte[] imageAsByteArray = Files.readAllBytes(file);
        Byte[] image = new Byte[imageAsByteArray.length];
        for (int i = 0; i < imageAsByteArray.length; i++) {
            image[i] = imageAsByteArray[i];
        }

        guacamole.setDescription("The best guacamole keeps it simple: just ripe avocados and a handful of flavorful mix-ins. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("1. Cut the avocado:\n" +
                "2.  Mash the avocado flesh:\n" +
                "3.  Add the remaining ingredients to taste:\n" +
                "4.  Serve immediately:\n");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(10);
        guacamole.setServings(10);
        guacamole.setSource("Simply recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setImage(image);
        guacamole.setNote(guacamoleNotes);
        guacamole.setIngredients(Set.of(avocado, kosherSalt, lemonJuice, mincedRedOnion, serranoChilis, cilantro, freshlyGroundBlackPepper,
                ripeTomato, redRadish, tortillaChips));
        guacamole.setCategories(Set.of(mexicanCategoty, americanCategory));
        return guacamole;
        //recipeRepository.save(guacamole);
    }
}
