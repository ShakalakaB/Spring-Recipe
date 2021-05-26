package aldora.spring.recipe.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;

}
