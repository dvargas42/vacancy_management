package br.com.vargas.vacancy_management.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.CompanyNotFountException;
import br.com.vargas.vacancy_management.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class ProfileCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public ProfileCompanyResponseDTO execute(UUID companyId) {
        CompanyEntity companyFound = companyRepository.findById(companyId)
            .orElseThrow(() -> {
                throw new CompanyNotFountException();
            });
        
            return ProfileCompanyResponseDTO.builder()
                .id(companyFound.getId())
                .name(companyFound.getName())
                .username(companyFound.getUsername())
                .email(companyFound.getEmail())
                .description(companyFound.getDescription())
                .build();
    }
    
}
