package br.com.vargas.vacancy_management.exceptions;

public class CompanyFoundException extends RuntimeException {
    
    public CompanyFoundException() {
        super("User already exists");
    }
}
