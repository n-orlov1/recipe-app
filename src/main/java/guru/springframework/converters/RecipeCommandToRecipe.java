package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NoteCommandToNote noteConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NoteCommandToNote notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.noteConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        Recipe result = null;
        if (source != null) {
            final Recipe recipe = new Recipe();
            recipe.setId(source.getId());
            recipe.setCookTime(source.getCookTime());
            recipe.setPrepTime(source.getPrepTime());
            recipe.setDescription(source.getDescription());
            recipe.setDifficulty(source.getDifficulty());
            recipe.setDirections(source.getDirections());
            recipe.setServings(source.getServings());
            recipe.setSource(source.getSource());
            recipe.setUrl(source.getUrl());
            recipe.setNote(noteConverter.convert(source.getNotes()));
            if (source.getCategories() != null && source.getCategories().size() > 0) {
                source.getCategories()
                        .forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
            }
            if (source.getIngredients() != null && source.getIngredients().size() > 0) {
                source.getIngredients()
                        .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
            }
            result = recipe;
        }

        return result;
    }
}
