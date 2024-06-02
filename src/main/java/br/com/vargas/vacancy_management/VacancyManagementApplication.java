package br.com.vargas.vacancy_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @OpenAPIDefinition(info = @Info(title = "Vacancy Management", description = "API responsible for managing vacancies", version = "1"))
// @SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class VacancyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyManagementApplication.class, args);
	}
}
