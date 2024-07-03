package br.com.vargas.vacancy_management.modules.job.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.CompanyNotFountException;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;
import br.com.vargas.vacancy_management.modules.job.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFountException();
        });
    return this.jobRepository.save(jobEntity);
    }
}
