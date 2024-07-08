package com.cir.cirback.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfesionalDTOSimp {
    private String dni;
    private String nombre;
    private String apellido;
    private Integer especialidadId;
}
