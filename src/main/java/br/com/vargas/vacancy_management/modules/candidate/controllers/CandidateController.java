package br.com.vargas.vacancy_management.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vargas.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.vargas.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.vargas.vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.vargas.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações das Candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa funcionalidade é responsável por cadastrar um candidato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        return ResponseEntity.ok().body(createCandidateUseCase.execute(candidateEntity));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa funcionalidade é responsável por buscar informação do perfil do candidato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProfileCandidateResponseDTO> read(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        return ResponseEntity.ok().body(profileCandidateUseCase.execute( UUID.fromString(candidateId.toString())));
    }
}
