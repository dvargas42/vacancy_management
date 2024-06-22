package br.com.vargas.vacancy_management.modules.company.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCompanyResponseDTO {

    private UUID id;

    @Schema(example = "Java Company Inc")
    private String name;

    @Schema(example = "java_company")
    private String username;

    @Schema(example = "java_company@email.com")
    private String email;

    @Schema(example = "Software house to create java application")
    private String description;
}
