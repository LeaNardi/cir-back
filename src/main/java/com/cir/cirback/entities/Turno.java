package com.cir.cirback.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Data
@NoArgsConstructor
public class Turno {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer turnoId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dniProfesional")
	private Profesional profesional;
	private LocalDate fecha;
	private LocalTime hora;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obraSocialId")
	private ObraSocial obraSocial;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
	private User paciente;
}
