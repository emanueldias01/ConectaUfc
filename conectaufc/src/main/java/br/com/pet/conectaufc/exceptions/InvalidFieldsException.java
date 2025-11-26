package br.com.pet.conectaufc.exceptions;

public class InvalidFieldsException extends RuntimeException {
    private final String exception = "Campos inválidos";

    public InvalidFieldsException(String message) {
        super(message);
    }

    public String getException() {
        return exception;
    }
}
