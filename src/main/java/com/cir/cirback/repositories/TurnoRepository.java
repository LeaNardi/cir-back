package com.cir.cirback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cir.cirback.entities.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer>{
	Turno[] findByDniProfesional(String dniProfesional);
}
