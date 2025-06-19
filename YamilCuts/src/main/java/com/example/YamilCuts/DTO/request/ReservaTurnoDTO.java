package com.example.YamilCuts.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaTurnoDTO {

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fecha;

    @NotNull(message = "La hora no puede ser nula")
    private LocalTime hora;

    @NotNull(message = "El ID del servicio no puede ser nulo")
    private Long idServicio;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    private String nombre;

    @NotBlank(message = "El número de WhatsApp es obligatorio")
    @Pattern(regexp = "^\\+54\\s?9?\\d{10}$", message = "WhatsApp debe tener formato argentino válido")
    private String whatsapp;
}


