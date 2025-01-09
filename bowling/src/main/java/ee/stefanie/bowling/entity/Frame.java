package ee.stefanie.bowling.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference  // märgistab player tagasi viite, et see ei oleks serialiseeritud iga Frame puhul, millel on juba oma Player objekt.
    private Player player;

    private int frameNumber;
    private Integer firstRoll;
    private Integer secondRoll;
    private Integer thirdRoll;
    private Integer score;
}
