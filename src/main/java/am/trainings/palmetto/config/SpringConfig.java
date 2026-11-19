package am.trainings.palmetto.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "am.trainings.palmetto.service",
        "am.trainings.palmetto.repository",
        "am.trainings.palmetto.json"
})
public class SpringConfig {
}
