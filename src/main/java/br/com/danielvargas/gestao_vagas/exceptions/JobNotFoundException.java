package br.com.danielvargas.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() {
        super("Vaga não existe");
    }
}
