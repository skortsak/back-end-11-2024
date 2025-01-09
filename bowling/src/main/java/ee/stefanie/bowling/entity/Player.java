package ee.stefanie.bowling.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @JsonManagedReference  // kasutatakse, et vältida tsüklit, serialiseerides frames ilma Player objekti kordamisele.
    private List<Frame> frames = new ArrayList<>();
}
