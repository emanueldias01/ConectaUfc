package br.com.pet.conectaufc.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private final String exception = "Entidade não encontrada";

    public EntityNotFoundException(String message) {
        super(message);
    }

    public String getException() {
        return exception;
    }
}
