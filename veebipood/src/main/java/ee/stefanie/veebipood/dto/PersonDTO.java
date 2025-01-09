package ee.stefanie.veebipood.dto;

import lombok.Data;

@Data // @Getter, @Setter, @NoArgsConstructor
public class PersonDTO {
    private String email;
    private String firstName;
    private String lastName;
}
