package com.example.YamilCuts.service;


import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;

import java.time.LocalDate;
import java.util.List;

public interface TurnoService {
    List<TurnosDisponiblesDTO> obtenerTurnosPorFecha(LocalDate fecha);

}


