package ee.stefanie.veebipood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // --> see koht asendab selle, et iga controlleri päises ei peaks kirjutama @CrossOrigin()
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // kõikidele API otspunktidele peale
                .allowedOrigins("http://localhost:4200")
                .allowedHeaders("*") // kõik headerid on lubatud
                .allowedMethods("*");
    }
}
