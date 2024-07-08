package com.cir.cirback.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cir.cirback.entities.ObraSocial;


@Repository
public interface ObraSocialRepository extends JpaRepository<ObraSocial, Integer> {

}
