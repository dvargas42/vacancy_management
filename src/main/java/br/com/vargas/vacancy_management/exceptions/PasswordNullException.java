package br.com.vargas.vacancy_management.exceptions;

public class PasswordNullException  extends RuntimeException {

    public PasswordNullException() {
        super("Password is null");
    }
}
