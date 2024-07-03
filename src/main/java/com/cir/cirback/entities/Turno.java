package com.cir.cirback.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Data
@NoArgsConstructor
public class Turno {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer turnoId;
	private String dniProfesional;
	private Date fecha;
	private int obraSocialId;
	private int pacienteId;
}
