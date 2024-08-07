package com.cir.cirback.entities;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // This tells Hibernate to make a table out of this class
@Data
@NoArgsConstructor
public class Profesional {
    @Id
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    
    private Date fechaIngreso;
    private boolean activo;
    private String motivobaja;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidadId")
    private Especialidad especialidad;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tituloId")
    private Titulo titulo;
    
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "formaciones", joinColumns = @JoinColumn(name = "profesional_dni"))
    @Column(name = "formacion", nullable = false)
    private List<String> formacionesComplementarias = new ArrayList<>();
    
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "publicaciones", joinColumns = @JoinColumn(name = "profesional_dni"))
    @Column(name = "publicacion", nullable = false)
    private List<String> publicacionesRevistas = new ArrayList<>();
    
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "presentaciones", joinColumns = @JoinColumn(name = "profesional_dni"))
    @Column(name = "presentacion", nullable = false)
    private List<String> presentacionesCongresos = new ArrayList<>();
    
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "experiencias", joinColumns = @JoinColumn(name = "profesional_dni"))
    @Column(name = "experiencia", nullable = false)
    private List<String> experienciaLaboral = new ArrayList<>();
}
