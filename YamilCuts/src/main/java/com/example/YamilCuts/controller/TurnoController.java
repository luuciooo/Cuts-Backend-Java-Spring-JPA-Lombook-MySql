package com.example.YamilCuts.controller;

import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;
import com.example.YamilCuts.model.Turno;
import com.example.YamilCuts.service.TurnoService;
import com.example.YamilCuts.service.impl.TurnoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/turnos")
@RequiredArgsConstructor
public class TurnoController {


    private final TurnoServiceImpl turnoService;

    @GetMapping("/{fecha}")
    public ResponseEntity<List<TurnosDisponiblesDTO>> obtenerTurnosPorFecha(
            @PathVariable String fecha
    ) {
        LocalDate fechaParseada;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            fechaParseada = LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        List<TurnosDisponiblesDTO> turnos = turnoService.obtenerTurnosPorFecha(fechaParseada);
        return ResponseEntity.ok(turnos);
    }

}


