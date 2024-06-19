package br.com.vargas.vacancy_management.modules.candidate.controllers;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vargas.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.vargas.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.vargas.vacancy_management.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações das Candidato")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;
    
    @PostMapping("/auth")
    @Operation(summary = "Autenticação do candidato", description = "Essa funcionalidade é responsável pela autenticação do candidato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var authCandidate = authCandidateUseCase.execute(authCandidateRequestDTO);
        return ResponseEntity.ok().body(authCandidate);
    }
}
