package com.cir.cirback.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cir.cirback.entities.Profesional;
import com.cir.cirback.repositories.EspecialidadRepository;
import com.cir.cirback.entities.Especialidad;
import com.cir.cirback.repositories.TituloRepository;
import com.cir.cirback.entities.Titulo;

@Component
public class ProfesionalMapper {
	@Autowired
    private EspecialidadRepository especialidadRepository;
	@Autowired
    private TituloRepository tituloRepository;
	
    public ProfesionalDTO profesionalToProfesionalDto(Profesional profesional) {
    	ProfesionalDTO profesionalDTO = new ProfesionalDTO();

        profesionalDTO.setDni(profesional.getDni());
        profesionalDTO.setNombre(profesional.getNombre());
        profesionalDTO.setApellido(profesional.getApellido());
        profesionalDTO.setEmail(profesional.getEmail());
        profesionalDTO.setDireccion(profesional.getDireccion());
        profesionalDTO.setTelefono(profesional.getTelefono());
        profesionalDTO.setFechaIngreso(profesional.getFechaIngreso());
        profesionalDTO.setActivo(profesional.isActivo());
        profesionalDTO.setMotivobaja(profesional.getMotivobaja());
        profesionalDTO.setEspecialidadId(profesional.getEspecialidad().getEspecialidadId());
        profesionalDTO.setTituloId(profesional.getTitulo().getTituloId());
        profesionalDTO.setFormacionesComplementarias(profesional.getFormacionesComplementarias());
        profesionalDTO.setPublicacionesRevistas(profesional.getPublicacionesRevistas());
        profesionalDTO.setPresentacionesCongresos(profesional.getPresentacionesCongresos());
        profesionalDTO.setExperienciaLaboral(profesional.getExperienciaLaboral());

        return profesionalDTO;
    }
    
    public ProfesionalDTOSimp profesionalToProfesionalDtoSimplified(Profesional profesional) {
    	ProfesionalDTOSimp profesionalDTOSimp = new ProfesionalDTOSimp();

    	profesionalDTOSimp.setDni(profesional.getDni());
    	profesionalDTOSimp.setNombre(profesional.getNombre());
    	profesionalDTOSimp.setApellido(profesional.getApellido());
    	profesionalDTOSimp.setEspecialidadId(profesional.getEspecialidad().getEspecialidadId());

        return profesionalDTOSimp;
    }

    public Profesional ProfesionalDtotoprofesional(ProfesionalDTO profesionalDTO) {
    	Profesional profesional = new Profesional();
    	
        profesional.setDni(profesionalDTO.getDni());
        profesional.setNombre(profesionalDTO.getNombre());
        profesional.setApellido(profesionalDTO.getApellido());
        profesional.setEmail(profesionalDTO.getEmail());
        profesional.setDireccion(profesionalDTO.getDireccion());
        profesional.setTelefono(profesionalDTO.getTelefono());
        profesional.setFechaIngreso(profesionalDTO.getFechaIngreso());
        profesional.setActivo(profesionalDTO.isActivo());
        profesional.setMotivobaja(profesionalDTO.getMotivobaja());
        Especialidad especialidad = especialidadRepository.findById(profesionalDTO.getEspecialidadId()).get();
        profesional.setEspecialidad(especialidad);
        Titulo titulo = tituloRepository.findById(profesionalDTO.getTituloId()).get();
        profesional.setTitulo(titulo);
        profesional.setFormacionesComplementarias(profesionalDTO.getFormacionesComplementarias());
        profesional.setPublicacionesRevistas(profesionalDTO.getPublicacionesRevistas());
        profesional.setPresentacionesCongresos(profesionalDTO.getPresentacionesCongresos());
        profesional.setExperienciaLaboral(profesionalDTO.getExperienciaLaboral());
    	
    	return profesional;
    }
}
