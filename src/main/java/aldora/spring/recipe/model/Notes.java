package aldora.spring.recipe.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;

}
