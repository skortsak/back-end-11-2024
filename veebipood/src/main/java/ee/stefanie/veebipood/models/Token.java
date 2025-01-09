package ee.stefanie.veebipood.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Token {
    private String token;
    private Date expiration;
    private boolean admin; // õiged õigused on Tokeni küljes, mis valideerib
    // läbi Tokeni kas isik saab kindlaid API ja päringuid teha.

    // see muutuja admin on selleks, et kas näidata admin nuppu või mitte.
}
