package br.com.vargas.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vargas.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.vargas.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.vargas.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID CandidateId) {
        var candidate = this.candidateRepository.findById(CandidateId)
            .orElseThrow(() -> {
                throw new CandidateNotFoundException();
            });
        
        return ProfileCandidateResponseDTO.builder()
            .id(candidate.getId())
            .name(candidate.getName())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .description(candidate.getDescription())
            .build();
    }
}