package aldora.spring.recipe.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Notes {

    @Id
    private String id;
    private String recipeNotes;
}
