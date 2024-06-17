package br.com.vargas.vacancy_management.modules.candidate.dto;

public record AuthCandidateRequestDTO(
    String username,
    String password
) {}