package com.example.YamilCuts.service.impl;

import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;
import com.example.YamilCuts.model.Feriado;
import com.example.YamilCuts.model.Turno;
import com.example.YamilCuts.repository.FeriadoRepository;
import com.example.YamilCuts.repository.TurnoRepository;
import com.example.YamilCuts.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final FeriadoRepository feriadoRepository;

    @Override
    public List<TurnosDisponiblesDTO> obtenerTurnosPorFecha(LocalDate fechaInicio) {
        // 1. Obtener las próximas 10 fechas hábiles (martes a sábados)
        List<LocalDate> fechasPosibles = new ArrayList<>();
        LocalDate fecha = fechaInicio;
        while (fechasPosibles.size() < 10) {
            DayOfWeek dia = fecha.getDayOfWeek();
            if (dia != DayOfWeek.SUNDAY && dia != DayOfWeek.MONDAY) {
                fechasPosibles.add(fecha);
            }
            fecha = fecha.plusDays(1);
        }

        // 2. Consultar feriados a través del servicio local o API
        List<LocalDate> feriados = feriadoRepository.findAll().stream()
                .map(Feriado::getFecha)
                .toList();

        // 3. Filtrar fechas que no sean feriados
        List<LocalDate> fechasValidas = fechasPosibles.stream()
                .filter(f -> !feriados.contains(f))
                .toList();

        // 4. Consultar turnos existentes en esas fechas
        List<Turno> turnosExistentes = turnoRepository.findByFechaIn(fechasValidas);

        // 5. Armar la lista de horarios por fecha (14 a 20)
        List<TurnosDisponiblesDTO> disponibles = new ArrayList<>();
        for (LocalDate f : fechasValidas) {
            List<LocalTime> horarios = Arrays.asList(
                    LocalTime.of(14, 0),
                    LocalTime.of(15, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(17, 0),
                    LocalTime.of(18, 0),
                    LocalTime.of(19, 0),
                    LocalTime.of(20, 0)
            );

            List<LocalTime> ocupados = turnosExistentes.stream()
                    .filter(t -> t.getFecha().equals(f))
                    .map(Turno::getHora)
                    .toList();

            List<LocalTime> libres = horarios.stream()
                    .filter(h -> !ocupados.contains(h))
                    .toList();

            if (!libres.isEmpty()) {
                disponibles.add(
                        TurnosDisponiblesDTO.builder()
                                .fecha(f)
                                .horariosDisponibles(libres)
                                .build()
                );
            }
        }

        return disponibles;
    }


}

