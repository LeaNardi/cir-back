package com.cir.cirback.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurnosGenerateDTO {
	private String profesionalDni;
	private LocalDate fecha;
	private String atencionInicio;
	private String atencionFin;
	private long duracion;
}
