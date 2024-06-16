package br.com.vargas.vacancy_management.exceptions;

public class JobNotFoundException extends RuntimeException {
    
    public JobNotFoundException() {
        super("Job not found.");
    }
}
