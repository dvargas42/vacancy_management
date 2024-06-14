package br.com.vargas.vacancy_management.exceptions;

public class CompanyNotFountException extends RuntimeException {

    public CompanyNotFountException() {
        super("Company not found");
    }
}
