package ee.stefanie.planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors().and().headers().xssProtection().disable().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/api/tasks").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/tasks/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tasks").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tasks/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/edit/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tasks").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/categories").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
