//package com.cir.cirback.entities;
//
//
//import jakarta.persistence.Entity;
//
//import jakarta.persistence.Id;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.List;
//
//import jakarta.persistence.*;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity // This tells Hibernate to make a table out of this class
//@Data
//@NoArgsConstructor
//public class Profesional {
//    @Id
//    private String DNI;
//    private String nombre;
//    private String apellido;
//    private String email;
//    private String direccion;
//    private String telefono;
//    
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "especialidadId")
//    private Especialidad especialidad;
//    
//    private Date fechaIngreso;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tituloId")
//    private Titulo titulo;
//    
//    private List<String> formacionesComplementarias;
//    private List<String> publicacionesRevistas;
//    private List<String> presentacionesCongresos;
//    private List<String> experienciaLaboral;
//    
//    
//}