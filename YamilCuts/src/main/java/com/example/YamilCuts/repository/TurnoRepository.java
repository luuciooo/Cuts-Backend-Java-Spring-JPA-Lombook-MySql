package com.example.YamilCuts.repository;

import com.example.YamilCuts.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByFechaIn(List<LocalDate> fechas);
    boolean existsByFechaAndHora(LocalDate fecha, LocalTime tiempo);
    Optional<Turno> findByCodigoUnico(String codigo);

}


