package br.com.danielvargas.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super("Company not found");
    }
}
