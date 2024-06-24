package com.cir.cirback.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cir.cirback.entities.Profesional;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Integer> {
    Optional<Profesional> findByDni(String dni);
    void deleteByDni(String dni);
    boolean existsByDni(String dni);
}