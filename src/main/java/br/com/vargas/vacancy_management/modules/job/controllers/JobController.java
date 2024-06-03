package br.com.vargas.vacancy_management.modules.job.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vargas.vacancy_management.modules.job.dto.CreateJobDTO;
import br.com.vargas.vacancy_management.modules.job.dto.ListAllJobsRequestDTO;
import br.com.vargas.vacancy_management.modules.job.entities.ApplyJobEntity;
import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;
import br.com.vargas.vacancy_management.modules.job.useCases.ApplyJobCandidateUseCase;
import br.com.vargas.vacancy_management.modules.job.useCases.CreateJobUseCase;
import br.com.vargas.vacancy_management.modules.job.useCases.ListAllJobsByFilterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
@Tag(name = "Vagas", description = "Informações das vagas")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
    
    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
        summary = "Cadastro de vagas", 
        description = "Essa funcionalidade é responsável por cadastrar as vagas dentro da empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = JobEntity.class))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<JobEntity> createJob(
        @Valid @RequestBody CreateJobDTO createJobDTO, 
        HttpServletRequest request) {
            
        String companyId = request.getAttribute("company_id").toString();

        JobEntity jobEntity = JobEntity.builder()
            .benefits(createJobDTO.getBenefits())
            .description(createJobDTO.getDescription())
            .level(createJobDTO.getLevel())
            .companyId(UUID.fromString(companyId))
            .build();

        return ResponseEntity.ok().body(this.createJobUseCase.execute(jobEntity));
    }

    


    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
        summary = "Listagem de vagas disponíveis para o candidato",
        description = "Essa funcionalidade é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
            )
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public Page<JobEntity> listAllJobsByFilter(
        @Valid @ModelAttribute ListAllJobsRequestDTO requestParams, 
        HttpServletRequest request) {

        String candidateId = request.getAttribute("candidate_id").toString();
        return listAllJobsByFilterUseCase.execute(UUID.fromString(candidateId), requestParams);
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Aplicação do candidato a uma vaga disponivel",
        description = "Essa funcionalidade é responsável por realizar a inscrição do candidato a uma vaga")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ApplyJobEntity> applyJob(@RequestBody UUID jobId, HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");
        ApplyJobEntity response = this.applyJobCandidateUseCase.execute(
            UUID.fromString(candidateId.toString()), jobId);
        return ResponseEntity.ok().body(response);
    }
}