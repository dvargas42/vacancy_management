package br.com.vargas.vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.vargas.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.vargas.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent( user -> {
                    throw new CandidateNotFoundException();
                });
        
        String password = passwordEncoder.encode(candidateEntity.getPassword());                    
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
