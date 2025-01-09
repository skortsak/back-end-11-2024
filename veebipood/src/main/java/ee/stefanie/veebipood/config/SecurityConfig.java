package ee.stefanie.veebipood.config;

import ee.stefanie.veebipood.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors().and().headers().xssProtection().disable().and()
                .csrf(AbstractHttpConfigurer::disable) // kui protection on peal, siis ei saa teha muid päringuid kui GET
                .authorizeHttpRequests(auth -> auth
                        // 2 varianti, kas tekitame nimekirja mida peab autentima või kuhu lubame
                        .requestMatchers(HttpMethod.GET,"/products").permitAll()
                        .requestMatchers(HttpMethod.GET,"/product/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categories").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/parcel-machines").permitAll()
                        .requestMatchers("/signup").permitAll()    // .hasRole("ROLE_admin")
                        .requestMatchers(HttpMethod.POST, "/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/products").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/categories").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH, "/make-admin").hasAuthority("admin") // super_admin
                        .anyRequest().authenticated()   ) // kõik ülejäänud päringud
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class); // iga päring läheb siia filtrisse kontrollimaks, kas pääsetakse läbi
        return http.build();
    }
}
