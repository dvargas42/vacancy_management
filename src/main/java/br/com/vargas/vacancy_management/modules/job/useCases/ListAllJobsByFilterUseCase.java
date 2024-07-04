package br.com.vargas.vacancy_management.modules.job.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.modules.job.dto.ListAllJobsRequestDTO;
import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;
import br.com.vargas.vacancy_management.modules.job.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;
    
    public Page<JobEntity> execute(UUID companyId, ListAllJobsRequestDTO requestParams) {
        Page<JobEntity> jobs = jobRepository.findByIdAndNameAndLevelAndBenefitsAndDescriptionAndIgnoreCase(
            companyId,
            requestParams.getLevel(), 
            requestParams.getBenefits(), 
            requestParams.getDescription(), 
            requestParams);
        return jobs;
    }   
}
