package br.com.vargas.vacancy_management.exceptions;

public class UsernameFoundException  extends RuntimeException {

    public UsernameFoundException() {
        super("Username already exists");
    }
}
