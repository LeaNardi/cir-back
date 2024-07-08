package com.cir.cirback.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurnoDTO {
    private Integer turnoId;
	private String profesionalDni;
	private LocalDate fecha;
	private LocalTime hora;
	private Integer obraSocialId;
	private Integer pacienteId;
}
