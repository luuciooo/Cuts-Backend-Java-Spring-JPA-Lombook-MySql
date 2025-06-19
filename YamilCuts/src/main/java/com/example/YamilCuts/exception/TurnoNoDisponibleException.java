package com.example.YamilCuts.exception;

public class TurnoNoDisponibleException extends RuntimeException {

    public TurnoNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public TurnoNoDisponibleException() {
        super("El turno solicitado ya está reservado.");
    }
}

