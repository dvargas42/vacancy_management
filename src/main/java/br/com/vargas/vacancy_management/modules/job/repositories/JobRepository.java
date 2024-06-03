package br.com.vargas.vacancy_management.modules.job.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vargas.vacancy_management.enums.LevelType;
import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    @Query("SELECT j FROM jobs j " + 
        "WHERE j.id = :id " +
        "AND lower(j.level) LIKE lower(concat('%', :level, '%')) " + 
        "AND lower(j.benefits) LIKE lower(concat('%', :benefits, '%')) " +
        "AND lower(j.description) LIKE lower(concat('%', :description, '%'))")
    Page<JobEntity> findByIdAndNameAndLevelAndBenefitsAndDescriptionAndIgnoreCase(
        UUID id,
        LevelType level,
        String benefits,
        String description,
        Pageable pageable);

}
