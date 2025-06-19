package com.example.YamilCuts.controller;

import com.example.YamilCuts.DTO.request.ReservaTurnoDTO;
import com.example.YamilCuts.DTO.response.TurnoCreadoDTO;
import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;
import com.example.YamilCuts.service.impl.TurnoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @PostMapping
    public ResponseEntity<TurnoCreadoDTO> reservarTurno(@RequestBody @Valid ReservaTurnoDTO dto) {
        TurnoCreadoDTO turno = turnoService.reservarTurno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(turno);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> cancelarTurno(@PathVariable String codigo) {
        turnoService.cancelarTurno(codigo);
        return ResponseEntity.ok().build();
    }


}


