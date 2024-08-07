package com.cir.cirback.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfesionalDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    private Date fechaIngreso;
    private boolean activo;
    private String motivobaja;
    private Integer especialidadId;
    private Integer tituloId;
    private List<String> formacionesComplementarias;
    private List<String> publicacionesRevistas;
    private List<String> presentacionesCongresos;
    private List<String> experienciaLaboral;
}
