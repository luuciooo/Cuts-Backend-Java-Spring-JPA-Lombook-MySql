package com.example.YamilCuts.service.impl;

import com.example.YamilCuts.DTO.request.ReservaTurnoDTO;
import com.example.YamilCuts.DTO.response.TurnoCreadoDTO;
import com.example.YamilCuts.DTO.response.TurnosDisponiblesDTO;
import com.example.YamilCuts.exception.TurnoNoEncontradoException;
import com.example.YamilCuts.model.Feriado;
import com.example.YamilCuts.model.Servicio;
import com.example.YamilCuts.model.Turno;
import com.example.YamilCuts.model.Usuario;
import com.example.YamilCuts.repository.FeriadoRepository;
import com.example.YamilCuts.repository.ServicioRepository;
import com.example.YamilCuts.repository.TurnoRepository;
import com.example.YamilCuts.repository.UsuarioRepository;
import com.example.YamilCuts.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.YamilCuts.exception.TurnoNoDisponibleException;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final FeriadoRepository feriadoRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;

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

    @Override
    public TurnoCreadoDTO reservarTurno(ReservaTurnoDTO dto) {
        boolean ocupado = turnoRepository.existsByFechaAndHora(dto.getFecha(), dto.getHora());
        if (ocupado) {
            throw new TurnoNoDisponibleException("El horario ya está ocupado");
        }

        Servicio servicio = servicioRepository.findById(dto.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        Usuario cliente = Usuario.builder()
                .nombre(dto.getNombre())
                .whatsapp(dto.getWhatsapp())
                .rol(Usuario.Rol.CLIENTE)
                .build();
        usuarioRepository.save(cliente);

        String codigo = generarCodigoUnico();

        Turno turno = Turno.builder()
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .usuario(cliente)
                .servicio(servicio)
                .codigoUnico(codigo)
                .build();
        turnoRepository.save(turno);

        //notificador.enviarConfirmacion(cliente.getWhatsapp(), codigo); // Implementalo si querés

        return TurnoCreadoDTO.builder()
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .nombre(dto.getNombre())
                .whatsapp(dto.getWhatsapp())
                .build();
    }

    public String generarCodigoUnico() {
        Random random = new SecureRandom();

        int numero = random.nextInt(900) + 100; // 100–999
        String letras = random.ints(3, 'A', 'Z' + 1)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());

        return numero + letras;
    }

    @Override
    public void cancelarTurno(String codigo) {
        Turno turno = turnoRepository.findByCodigoUnico(codigo)
                .orElseThrow(() -> new TurnoNoEncontradoException("Turno no encontrado"));

        turnoRepository.delete(turno);

        //notificador.notificarCancelacionAlAdmin(turno); // ⚠️ Implementar esta parte en WhatsAppNotificador
    }



}

