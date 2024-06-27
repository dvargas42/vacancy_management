package br.com.vargas.vacancy_management.modules.company.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyResponseDTO {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;
    private String website;
}
