package aldora.spring.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipe = new HashSet<>();

}
