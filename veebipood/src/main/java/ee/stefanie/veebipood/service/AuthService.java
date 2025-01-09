package ee.stefanie.veebipood.service;

import ee.stefanie.veebipood.entity.Person;
import ee.stefanie.veebipood.models.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    public Token getToken(Person person) {

        Date expirationDate = new Date((new Date()).getTime() + 20 * 60 * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", person.getId());
        claims.put("email", person.getEmail());
        claims.put("admin", person.isAdmin());

        String securityKey = "jdAj1WqQMyOOrxRk9umHDLGO+589g1ligaL6iBLIzE0=";
        Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));

        String jwtToken = Jwts.builder()
                .expiration(expirationDate)
                .claims(claims)
                .signWith(signingKey)
                .compact();

        return new Token(jwtToken, expirationDate, person.isAdmin());
    }
}
