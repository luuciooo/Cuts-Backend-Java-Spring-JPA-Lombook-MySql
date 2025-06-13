package com.example.YamilCuts.repository;

import com.example.YamilCuts.model.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Long> {

    List<Feriado> findAll();
}

