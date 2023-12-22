package br.com.danielvargas.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(requiredMode = RequiredMode.AUTO)
    private UUID id;

    @Schema(example = "Daniel Vargas", requiredMode = RequiredMode.REQUIRED)
    private String name;
    
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
    @Schema(example = "dvargas42", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    @Schema(example = "dvargas@mail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 10, max = 100, message = "O campo [password] deve ter no mínimo 10 caracteres")
    @Schema(example = "test@12345", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Schema(example = "Desenvolvedor Java Senior", requiredMode = RequiredMode.REQUIRED)
    private String description;
    
    @Schema(example = "Colocar todas as informações de formação", requiredMode = RequiredMode.REQUIRED)
    private String curriculum;

    @CreationTimestamp
    @Schema(requiredMode = RequiredMode.AUTO)
    private LocalDateTime createdAt;
}
