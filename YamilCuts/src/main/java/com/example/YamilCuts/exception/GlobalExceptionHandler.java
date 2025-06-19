package com.example.YamilCuts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TurnoNoDisponibleException.class)
    public ResponseEntity<Map<String, String>> manejarTurnoNoDisponible(TurnoNoDisponibleException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    @ExceptionHandler(TurnoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarTurnoNoEncontrado(TurnoNoEncontradoException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }


}

