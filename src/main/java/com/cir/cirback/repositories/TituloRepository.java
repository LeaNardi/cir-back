package com.cir.cirback.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cir.cirback.entities.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Integer> {
    Optional<Titulo> findByTitulo(String titulo);
}