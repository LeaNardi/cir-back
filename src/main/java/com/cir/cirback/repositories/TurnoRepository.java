package com.cir.cirback.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cir.cirback.entities.Profesional;
import com.cir.cirback.entities.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer>{
	List<Turno> findByProfesional(Profesional profesional);
	List<Turno> findByProfesionalAndFecha(Profesional profesional, LocalDate fecha);
}
