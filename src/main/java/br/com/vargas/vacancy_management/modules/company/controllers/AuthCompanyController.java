package br.com.vargas.vacancy_management.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vargas.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.vargas.vacancy_management.modules.company.dto.AuthCompanyRequestDTO;
import br.com.vargas.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.vargas.vacancy_management.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Informações da Empresa")
public class AuthCompanyController {
    
    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Autenticação da empresa", description = "Essa funcionalidade é responsável pela autenticação do empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public AuthCompanyResponseDTO create(@Valid @RequestBody AuthCompanyRequestDTO authCompanyRequestDTO)
            throws AuthenticationException {
        var response = this.authCompanyUseCase.execute(authCompanyRequestDTO);
        return response;
    }
}
