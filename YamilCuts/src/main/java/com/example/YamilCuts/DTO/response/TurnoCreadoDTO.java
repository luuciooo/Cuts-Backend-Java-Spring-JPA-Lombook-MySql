package com.example.YamilCuts.DTO.response;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoCreadoDTO {
    private LocalDate fecha;
    private LocalTime hora;
    private String nombre;
    private String whatsapp;
}

