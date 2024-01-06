package br.com.danielvargas.gestao_vagas.modules.candidate.dto;

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
public class ProfileCandidateResponseDTO {
    
    private UUID id;

    @Schema(example = "Daniel Vargas")
    private String name;

    @Schema(example = "dvargas42")
    private String username;

    @Schema(example = "dvargas@mail.com")
    private String email;

    @Schema(example = "Desenvoldor Java")
    private String description;
}
