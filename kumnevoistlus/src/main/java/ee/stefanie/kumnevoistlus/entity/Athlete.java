package ee.stefanie.kumnevoistlus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private Integer age;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private List<Event> events;

    public Athlete(String name, String country, Integer age) {
        this.name = name;
        this.country = country;
        this.age = age;
    }
}
