package com.example.YamilCuts.repository;

import com.example.YamilCuts.model.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Long> {

}

