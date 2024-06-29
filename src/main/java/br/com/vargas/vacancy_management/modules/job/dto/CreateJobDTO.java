package br.com.vargas.vacancy_management.modules.job.dto;

import br.com.vargas.vacancy_management.enums.LevelType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    
    @Schema(example = "Vaga para desenvolvedor júnior", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "GYMPass, Plano de saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    //@NotBlank(message = "This field is required.")
    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private LevelType level;
}
