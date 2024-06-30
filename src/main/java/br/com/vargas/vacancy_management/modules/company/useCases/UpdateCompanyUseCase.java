package br.com.vargas.vacancy_management.modules.company.useCases;

import java.util.UUID;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.EmailFoundException;
import br.com.vargas.vacancy_management.exceptions.PasswordNullException;
import br.com.vargas.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.vargas.vacancy_management.exceptions.UsernameFoundException;
import br.com.vargas.vacancy_management.modules.company.dto.UpdateCompanyRequestDTO;
import br.com.vargas.vacancy_management.modules.company.dto.UpdateCompanyResponseDTO;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class UpdateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UpdateCompanyResponseDTO execute(
        UUID companyId, 
        UpdateCompanyRequestDTO request) throws AuthenticationException {
    
        CompanyEntity companyFound = companyRepository.findById(companyId)
            .orElseThrow(() -> {
                throw new CandidateNotFoundException();
            });

        if (request.getPassword() == null && (request.getNewPassword() != null ||
            request.getEmail() != null)) {
            throw new PasswordNullException();
        }

        if (request.getPassword() != null) {
            boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), 
                companyFound.getPassword());
    
            if (!passwordMatches) {
                throw new AuthenticationException();
            }

            if (request.getNewPassword() != null) {
                String newPassword = passwordEncoder.encode(request.getNewPassword());
                companyFound.setPassword(newPassword);
            }

            if (request.getEmail() != null) {
                companyRepository.findByEmail(request.getEmail())
                    .ifPresent(user -> {
                        throw new EmailFoundException();
                    });
                companyFound.setEmail(request.getEmail());
            }
        }

        if (request.getUsername() != null) {
            companyRepository.findByUsername(
                request.getUsername()).ifPresent(user -> {
                    throw new UsernameFoundException();
                });;
            companyFound.setUsername(request.getUsername());
        }

        companyFound.setName(request.getName() != null 
            ? request.getName() 
            : companyFound.getName());

        companyFound.setDescription(request.getDescription() != null
            ? request.getDescription()
            : companyFound.getDescription());
        
        companyFound.setWebsite(request.getWebsite() != null
            ? request.getWebsite()
            : companyFound.getWebsite());

        CompanyEntity companySaved = companyRepository.save(companyFound);

        return UpdateCompanyResponseDTO.builder()
            .id(companySaved.getId())
            .name(companySaved.getName())
            .username(companySaved.getUsername())
            .email(companySaved.getEmail())
            .description(companySaved.getDescription())
            .website(companySaved.getWebsite())
            .build();
        
    }
}
