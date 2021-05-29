package aldora.spring.recipe.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Byte[] image;
    private Notes notes;
    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes == null) {
            return;
        }

        this.notes = notes;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
