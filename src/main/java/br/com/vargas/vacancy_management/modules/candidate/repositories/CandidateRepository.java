package br.com.vargas.vacancy_management.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vargas.vacancy_management.modules.candidate.entities.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{

    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

    Optional<CandidateEntity> findByUsername(String username);
}
