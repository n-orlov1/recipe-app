package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    @Override
    public String toString() {
        return "id is " + id + " recipeId is " + recipeId + " description is " + description + " amount is " + amount +
        " uom is " + unitOfMeasure;
    }

}