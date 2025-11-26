package br.com.pet.conectaufc.exceptions;

public class BussinessException extends RuntimeException {
    private final String exception = "Erro de regra de negócio";

    public BussinessException(String message) {
        super(message);
    }

    public String getException() {
        return exception;
    }
}
