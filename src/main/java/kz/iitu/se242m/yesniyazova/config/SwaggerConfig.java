package kz.iitu.se242m.yesniyazova.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecoDataOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoData REST API")
                        .description("Air quality, weather and admin endpoints")
                        .version("v1.0"));
    }
}