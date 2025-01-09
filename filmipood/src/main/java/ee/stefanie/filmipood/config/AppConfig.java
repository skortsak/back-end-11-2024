package ee.stefanie.filmipood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Tegime @Transactional pärast, praegu kasutu, pärast teeme korda

@EnableTransactionManagement
@Configuration
public class AppConfig {
}
