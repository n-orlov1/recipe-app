package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.entities.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    @Override
    @Nullable
    @Transactional
    public IngredientCommand convert(Ingredient ingredient) {
        return null;
    }
}
