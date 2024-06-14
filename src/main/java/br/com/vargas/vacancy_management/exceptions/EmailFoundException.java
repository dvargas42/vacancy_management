package br.com.vargas.vacancy_management.exceptions;

public class EmailFoundException extends RuntimeException {

    public EmailFoundException() {
        super("Email already exists");
    }
}
