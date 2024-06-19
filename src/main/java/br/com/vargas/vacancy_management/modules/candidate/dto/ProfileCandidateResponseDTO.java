package br.com.vargas.vacancy_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    
    private UUID id;
    
    @Schema(example = "Daniel Vargas")
    private String name;

    @Schema(example = "dvargas42")
    private String username;

    @Schema(example = "dvargas@gmail.com")
    private String email;

    @Schema(example = "Desenvolvedor Java")
    private String description;
}
