package com.example.YamilCuts.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnosDisponiblesDTO {
    private LocalDate fecha;
    private List<LocalTime> horariosDisponibles;
}
