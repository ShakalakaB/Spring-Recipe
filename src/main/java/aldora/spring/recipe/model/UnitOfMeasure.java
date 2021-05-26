package aldora.spring.recipe.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String description;

}
