package br.com.vargas.vacancy_management.modules.job.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.JobNotFoundException;
import br.com.vargas.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.vargas.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.vargas.vacancy_management.modules.job.entities.ApplyJobEntity;
import br.com.vargas.vacancy_management.modules.job.repositories.ApplyJobRepository;
import br.com.vargas.vacancy_management.modules.job.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {

        this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new CandidateNotFoundException();
            });
        
        this.jobRepository.findById(jobId)
            .orElseThrow(() -> {
                throw new JobNotFoundException();
            });

        ApplyJobEntity applyJobEntity = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(jobId)
            .build();
        
        ApplyJobEntity applyJob = applyJobRepository.save(applyJobEntity);

        return applyJob;
    }
}
