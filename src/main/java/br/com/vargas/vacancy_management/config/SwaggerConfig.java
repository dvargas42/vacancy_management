package br.com.vargas.vacancy_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(information())
                .schemaRequirement("jwt_auth", createSecurityScheme());
        // .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        // // token para todos os endpoint
        // .components(new Components().addSecuritySchemes("Bearer Authentication",
        // createSecurityScheme()));
    }

    private Info information() {
        return new Info().title("Vacancy Management")
                .description("API responsible for managing vacancies")
                .version("1");
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER);
    }
}
// @OpenAPIDefinition(info = @Info(title = "Vacancy Management", description =
// "API responsible for managing vacancies", version = "1"))
// @SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT",
// type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
