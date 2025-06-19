package com.example.YamilCuts.service;


import com.example.YamilCuts.DTO.request.ReservaTurnoDTO;
import com.example.YamilCuts.DTO.response.TurnoCreadoDTO;
import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;


import java.time.LocalDate;
import java.util.List;

public interface TurnoService {
    List<TurnosDisponiblesDTO> obtenerTurnosPorFecha(LocalDate fecha);
    TurnoCreadoDTO reservarTurno(ReservaTurnoDTO dto);
    void cancelarTurno(String codigo);
}


