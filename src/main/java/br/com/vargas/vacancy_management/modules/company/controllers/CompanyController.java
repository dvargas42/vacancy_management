package br.com.vargas.vacancy_management.modules.company.controllers;

import java.util.UUID;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vargas.vacancy_management.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.vargas.vacancy_management.modules.company.dto.UpdateCompanyRequestDTO;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.useCases.CreateCompanyUseCase;
import br.com.vargas.vacancy_management.modules.company.useCases.ProfileCompanyUseCase;
import br.com.vargas.vacancy_management.modules.company.useCases.UpdateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa", description = "Informações da Empresa")
public class CompanyController {
    
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Autowired
    private ProfileCompanyUseCase profileCompanyUseCase;

    @Autowired
    private UpdateCompanyUseCase updateCompanyUseCase;

    @PostMapping("/")
    @Operation(summary = "Perfil da empresa", description = "Essa funcionalidade é responsável por buscar informação do perfil do empresa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CompanyEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CompanyEntity> createCompany(@Valid @RequestBody CompanyEntity companyEntity) {
        return ResponseEntity.ok().body(this.createCompanyUseCase.execute(companyEntity));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<ProfileCompanyResponseDTO> companyProfile(HttpServletRequest request) {
        String companyId = request.getAttribute("company_id").toString();
        return ResponseEntity.ok().body(profileCompanyUseCase.execute(UUID.fromString(companyId)));
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> updateCompany(
        @Valid @RequestBody UpdateCompanyRequestDTO updateCompanyRequestDTO, 
        HttpServletRequest request) throws AuthenticationException  {

        String companyId = request.getAttribute("company_id").toString();
        return ResponseEntity.ok().body(updateCompanyUseCase.execute(
            UUID.fromString(companyId), updateCompanyRequestDTO));
    }
}