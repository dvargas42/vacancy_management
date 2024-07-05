package br.com.vargas.vacancy_management.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vargas.vacancy_management.exceptions.JobNotFoundException;
import br.com.vargas.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.vargas.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.vargas.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.vargas.vacancy_management.modules.job.entities.ApplyJobEntity;
import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;
import br.com.vargas.vacancy_management.modules.job.repositories.ApplyJobRepository;
import br.com.vargas.vacancy_management.modules.job.repositories.JobRepository;
import br.com.vargas.vacancy_management.modules.job.useCases.ApplyJobCandidateUseCase;


@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;
    
    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CandidateNotFoundException.class);
        }
    }

    @Test
    void shouldNotBeAbleToApplyJobWithJobNotFound() {
        UUID candidateId = UUID.randomUUID();
        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(candidateId);

        when(candidateRepository.findById(candidateId))
            .thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(candidateId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    void shouldBeAbleToCreateANewApllyJob() {
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        // CandidateEntity candidate = new CandidateEntity();
        // candidate.setId(candidateId);

        // JobEntity job = new JobEntity();
        // job.setId(jobId);

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(jobId)
            .build();

        ApplyJobEntity applyJobEntity = ApplyJobEntity.builder()
            .id(UUID.randomUUID())
            .build();

        when(candidateRepository.findById(candidateId))
            .thenReturn(Optional.of(new CandidateEntity()));

        when(jobRepository.findById(jobId))
            .thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobEntity);

        ApplyJobEntity result = applyJobCandidateUseCase.execute(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
        assertThat(result.getId()).isInstanceOf(UUID.class);
        
    }
}
