package ee.stefanie.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // @ColumnDefault("false")
    private boolean admin; // kui väärtust pole andmebaasis, sisi on "null"

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}

//{
//        "email": "test@test.com",
//        "password": "123456",
//        "firstName": "First",
//        "lastName": "Last",
//        "address": {
//        "street": "Tammsaare",
//        "houseNo": "72",
//        "apatmentNo": "3",
//        "postalIndex": "71122",
//        "city": "Tallinn",
//        "county": "Harjumaa",
//        "country": "Estonia"
//        }
//        }