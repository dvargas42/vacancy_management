package br.com.vargas.vacancy_management.exceptions;

public class CandidateNotFoundException extends RuntimeException {

    public CandidateNotFoundException() {
        super("Candidate not found.");
    }
}
