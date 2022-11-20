package guru.springframework.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    @Lob
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Note note;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;
    @ManyToMany
    @JoinTable(name = "recipe_category",
                joinColumns = @JoinColumn(name = "recipe_id"),
                inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        for(Ingredient ingredient : this.ingredients) {
            setRecipeForIngredient(ingredient);
        }
    }

    private void setRecipeForIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
    }

    public void setNote(Note note) {
        this.note = note;
        note.setRecipe(this);
    }
}
