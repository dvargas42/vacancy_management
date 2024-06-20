package br.com.vargas.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.CompanyFoundException;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent(user -> {
                    throw new CompanyFoundException();
                });
        String password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        
        return this.companyRepository.save(companyEntity);
    }
}
